/**
@author Parth Mittal (2015069)
@author Rishabh Garg (2015076)
*/
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;

public class Query3Handler extends DefaultHandler {
    private Integer yearUpto;
    private HashMap<String, Person> authors;
    private HashMap<Person, Integer> numPublicationsProjected;
    private HashMap<Person, Integer> earliestPublication;
    private ArrayList<Person> currentAuthors;
    private Integer currentYear;
    private boolean store;
    private String buffer;
    private static final Set<String> PUB_TYPES = new HashSet<>(Arrays.asList("article", "book", "incollection", "inproceedings", "phdthesis", "proceedings", "mastersthesis"));

    public Query3Handler(HashMap<String, Person> authors, Integer yearUpto) {
        this.yearUpto = yearUpto;
        this.authors = authors;
    }

    @Override
    public void startDocument() throws SAXException {
        currentAuthors = new ArrayList<>();
        currentYear = null;
        buffer = new String();
        numPublicationsProjected = new HashMap<>();
        earliestPublication = new HashMap<>();
    }

    @Override
    public void endDocument() throws SAXException {
        for (Map.Entry<Person, Integer> cur : numPublicationsProjected.entrySet()) {
            if (cur.getValue() == null) {
                cur.setValue(0);
            } else {
                Integer first = earliestPublication.get(cur.getKey());
                Integer yearsActive = yearUpto - first;
                Integer predictFor = 2016 - first;
                Integer prediction = new Integer((int) (new Double(cur.getValue()) / yearsActive * predictFor + 0.5));
                cur.setValue(prediction);
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equals("author") || localName.equals("year")) {
            store = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (store) {
            if (localName.equals("author")) {
                if (authors.get(buffer) == null) {
                    System.out.printf("buffer %s not found\n", buffer);
                } else {
                    currentAuthors.add(authors.get(buffer));
                }
            } else if (localName.equals("year")) {
                currentYear = Integer.parseInt(buffer);
            }
            buffer = new String();
            store = false;
        } else if (PUB_TYPES.contains(localName)) {
            if (currentYear != null && currentYear <= yearUpto) {
                for (Person currentPerson : currentAuthors) {
                    Integer val = numPublicationsProjected.get(currentPerson);
                    if (val == null) {
                        numPublicationsProjected.put(currentPerson, 1);
                    } else {
                        numPublicationsProjected.put(currentPerson, val + 1);
                    }
                    val = earliestPublication.get(currentPerson);
                    if (val == null) {
                        earliestPublication.put(currentPerson, currentYear);
                    } else {
                        earliestPublication.put(currentPerson, Math.min(currentYear, val));
                    }
                }
            }
            currentYear = null;
            currentAuthors = new ArrayList<>();
        }
    }

    public HashMap<Person, Integer> getNumPublicationsProjected() {
        return numPublicationsProjected;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (store) {
            buffer += new String(ch, start, length);
        }
    }
}

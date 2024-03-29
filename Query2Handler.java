/**
@author Parth Mittal (2015069)
@author Rishabh Garg (2015076)
*/
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;

public class Query2Handler extends DefaultHandler {
    private HashMap<Person, Integer> numPublications;
    private HashMap<String, Person> authors;
    private static final Set<String> PUB_TYPES = new HashSet<>(Arrays.asList("article", "book", "incollection", "inproceedings", "phdthesis", "proceedings", "mastersthesis"));
    private String buffer;
    private boolean store;

    public Query2Handler(HashMap<String, Person> authors) {
        this.authors= authors;
    }

    @Override
    public void startDocument() throws SAXException {
        numPublications = new HashMap<>();
        buffer = new String();
        store = false;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equals("author")) {
            store = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (store) {
            if (!localName.equals("author")) {
                throw new SAXException("what the fuck");
            }
            Person author = this.authors.get(buffer);
            if (author == null) {
                throw new SAXException("double what the fuck");
            }
            Integer val = numPublications.get(author);
            if (val == null) {
                numPublications.put(author, 1);
            } else {
                numPublications.put(author, val + 1);
            }
            store = false;
            buffer = new String();
        }
    }

    public HashMap<Person, Integer> getNumPublications() {
        return numPublications;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (store) {
            buffer += new String(ch, start, length);
        }
    }
}

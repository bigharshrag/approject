import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;

public class Query1aHandler extends DefaultHandler{
    private Publication currentPublication;
    private ArrayList<Publication> ret;
    private Person author;
    private static final Set<String> PUB_TYPES = new HashSet<>(Arrays.asList("article", "book", "incollection", "inproceedings", "phdthesis", "proceedings", "mastersthesis"));
    private boolean store;
    private boolean authorFound;
    private String buffer;

    public Query1aHandler(Person author) {
        this.author = author;
    }

    public void startDocument() throws SAXException {
        currentPublication = null;
        ret = new ArrayList<>();
        store = false;
        authorFound = false;
        buffer = new String();
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (PUB_TYPES.contains(localName)) {
            currentPublication = new Publication(localName);
            store = true;
            authorFound = false;
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (PUB_TYPES.contains(localName)) {
            if (authorFound) {
                ret.add(currentPublication);
            }
            ret = new ArrayList<>();
            store = false;
            authorFound = false;
        } else if (store) {
            switch (localName) {
                case "author":
                    currentPublication.addAuthor(buffer);
                    if (!authorFound && author.getNames().contains(buffer)) {
                        authorFound = true;
                    }
                    break;
                case "booktitle":
                    currentPublication.setBookTitle(buffer);
                    break;
                case "journal":
                    currentPublication.setJournal(buffer);
                    break;
                case "pages":
                    currentPublication.setPages(buffer);
                    break;
                case "title":
                    currentPublication.setTitle(buffer);
                    break;
                case "url":
                    currentPublication.setUrl(buffer);
                    break;
                case "volume":
                    currentPublication.setVolume(buffer);
                    break;
                case "year":
                    currentPublication.setYear(Integer.parseInt(buffer));
                    break;
                default:
                    break;
            }
        }
        buffer = new String();
    }

    public ArrayList<Publication> getRet() {
        return ret;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (store) {
            buffer += new String(ch, start, length);
        }
    }
}

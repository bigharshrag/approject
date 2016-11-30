/**
@author Parth Mittal (2015069)
@author Rishabh Garg (2015076)
*/
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;

public class Query1Handler extends DefaultHandler {
    protected Publication currentPublication;
    protected static final Set<String> PUB_TYPES = new HashSet<>(Arrays.asList("article", "book", "incollection", "inproceedings", "phdthesis", "proceedings", "mastersthesis"));
    protected boolean store;
    protected String buffer;
    protected ArrayList<Pair<Integer, Publication>> ret;

    public void startDocument() throws SAXException {
        currentPublication = null;
        store = false;
        buffer = new String();
        ret = new ArrayList<>();
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (PUB_TYPES.contains(localName)) {
            currentPublication = new Publication(localName);
            store = true;
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!PUB_TYPES.contains(localName) && store) {
            switch (localName) {
                case "author":
                    currentPublication.addAuthor(buffer);
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
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (store) {
            buffer += new String(ch, start, length);
        }
    }

    public ArrayList<Pair<Integer, Publication>> getRet() {
        return ret;
    }
}

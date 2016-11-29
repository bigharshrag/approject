import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;

public class Query1aHandler extends Query1Handler {
    private Person author;
    private boolean authorFound;
    private ArrayList<Publication> ret;

    public Query1aHandler(Person author) {
        this.author = author;
    }

    public void startDocument() throws SAXException {
        super.startDocument();
        ret = new ArrayList<>();
        authorFound = false;
    }

    public ArrayList<Publication> getRet() {
        return ret;
    }


    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (PUB_TYPES.contains(localName)) {
            if (authorFound) {
                ret.add(currentPublication);
            }
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

    public void endDocument() throws SAXException {
        System.out.printf("Found %d publications\n", ret.size());
    }
}

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;

public class Query1bHandler extends Query1Handler {
    ArrayList<String> keywords;
    ArrayList<Pair<Integer, Publication>> ret;

    public Query1bHandler(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public void startDocument() throws SAXException {
        super.startDocument();
        ret = new ArrayList<>();
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (PUB_TYPES.contains(localName)) {
            int count = 0;
            for (String keyword : keywords) {
                if (currentPublication.getTitle().contains(keyword)) {
                    ++count;
                }
            }
            if (count * 2 >= keywords.size()) {
                ret.add(new Pair<>(count, currentPublication));
            }
            store = false;
        } else if (store) {
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
        buffer = new String();
    }

    public ArrayList<Pair<Integer, Publication>> getRet() {
        return ret;
    }
}

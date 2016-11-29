import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;

public class Query1bHandler extends Query1Handler {
    ArrayList<String> keywords;

    public Query1bHandler(ArrayList<String> keywords) {
        this.keywords = keywords;
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
                ret.add(new Pair<>(-count, currentPublication));
            }
            store = false;
        } else {
            super.endElement(uri, localName, qName);
        }
        buffer = new String();
    }

    public ArrayList<Pair<Integer, Publication>> getRet() {
        return ret;
    }
}
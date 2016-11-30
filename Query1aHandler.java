/**
@author Parth Mittal (2015069)
@author Rishabh Garg (2015076)
*/
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;

public class Query1aHandler extends Query1Handler {
    private Person author;

    public Query1aHandler(Person author) {
        this.author = author;
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (PUB_TYPES.contains(localName)) {
            for (String authorName : currentPublication.getAuthors()) {
                if (author.getNames().contains(authorName)) {
                    ret.add(new Pair<>(author.getNames().indexOf(authorName), currentPublication));
                    break;
                }
            }
            store = false;
        } else {
            super.endElement(uri, localName, qName);
        }
        buffer = new String();
    }

    public void endDocument() throws SAXException {
        System.out.printf("Found %d publications\n", ret.size());
    }
}

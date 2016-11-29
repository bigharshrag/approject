import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;

public class Query1Handler extends DefaultHandler {
    protected Publication currentPublication;
    protected static final Set<String> PUB_TYPES = new HashSet<>(Arrays.asList("article", "book", "incollection", "inproceedings", "phdthesis", "proceedings", "mastersthesis"));
    protected boolean store;
    protected String buffer;

    public void startDocument() throws SAXException {
        currentPublication = null;
        store = false;
        buffer = new String();
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (PUB_TYPES.contains(localName)) {
            currentPublication = new Publication(localName);
            store = true;
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (store) {
            buffer += new String(ch, start, length);
        }
    }
}

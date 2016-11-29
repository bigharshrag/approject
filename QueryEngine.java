import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class QueryEngine {
    private HashMap<String, Person> authors;
    private ArrayList<Person> authorsList;
    private String filename;

    public QueryEngine(String filename) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        AuthorHandler handler = new AuthorHandler();
        xmlReader.setContentHandler(handler);
        xmlReader.parse(convertToFileURL(filename));

        this.filename = filename;

        authors = handler.getAuthors();
        authorsList = handler.getAuthorsList();
    }

    private static String convertToFileURL(String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    public ArrayList<Publication> query1a(String authorName) throws IOException, SAXException, ParserConfigurationException {
        Person value;
        if ((value = authors.get(authorName)) != null) {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            Query1aHandler handler = new Query1aHandler(value);
            xmlReader.setContentHandler(handler);
            xmlReader.parse(convertToFileURL(this.filename));
            return handler.getRet();
        } else {
            System.out.println("Author not found");
            return new ArrayList<>();
        }
    }
}

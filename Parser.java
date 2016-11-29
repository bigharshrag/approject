import org.xml.sax.*;
import org.xml.sax.helpers.*;

import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class Parser extends DefaultHandler {
    private Hashtable<String, Integer> tags;
    private Set<Publication> publications;
    private Publication currentPublication;
    private String buffer;
    private static final Set<String> PUB_TYPES = new HashSet<>(Arrays.asList("article", "book", "incollection", "inproceedings", "phdthesis", "proceedings", "www"));

    public void startDocument() throws SAXException {
        tags = new Hashtable<>();
        publications = new HashSet<>();
        currentPublication = null;
        buffer = new String();
    }

    public void endDocument() throws SAXException {
        System.out.println("what the fuck");
        Enumeration e = tags.keys();
        while(e.hasMoreElements()) {
            String tag = (String) e.nextElement();
            int count = tags.get(tag);
            System.out.println(tag);
        }
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        String key = localName;
        Integer value = tags.get(key);

        if (PUB_TYPES.contains(localName)) {
            assert(currentPublication == null);
            currentPublication = new Publication(localName);
            currentPublication.setKey(attrs.getValue(1));
        }

        if (value == null) {
            tags.put(key, new Integer(1));
        } else {
            tags.put(key, value + 1);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (PUB_TYPES.contains(localName)) {
            assert(currentPublication != null);
            if (currentPublication.getAuthors().get(0).endsWith("a")) {
                publications.add(currentPublication);
            }
            currentPublication = null;
        } else if (!localName.equals("dblp")){

            if (currentPublication == null) {
                System.out.print(localName + ' ' + buffer);
                return;
            }
            switch(localName) {
                case "author":
                    currentPublication.addAuthor(buffer);
                    break;
                case "booktitle":
                    currentPublication.setBookTitle(buffer);
                    break;
                case "crossref":
                    currentPublication.setCrossref(buffer);
                    break;
                case "editor":
                    currentPublication.addEditor(buffer);
                    break;
                case "ee":
                    currentPublication.setEe(buffer);
                    break;
                case "journal":
                    currentPublication.setJournal(buffer);
                    break;
                case "number":
                    currentPublication.setNumber(buffer);
                    break;
                case "pages":
                    currentPublication.setPages(buffer);
                    break;
                case "publisher":
                    currentPublication.setPublisher(buffer);
                    break;
                case "school":
                    currentPublication.setSchool(buffer);
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

            buffer = new String();
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer += new String(ch, start, length);
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

    public static void main(String args[]) throws ParserConfigurationException, org.xml.sax.SAXException, IOException {
        Scanner in =  new Scanner(System.in);
        String filename = "dblp.xml";

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(new Parser());
        xmlReader.parse(convertToFileURL(filename));
    }
}

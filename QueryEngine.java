/**
@author Parth Mittal (2015069)
@author Rishabh Garg (2015076)
*/
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import javax.xml.parsers.*;
import org.xml.sax.*;

public class QueryEngine {
    private HashMap<String, Person> authors;
    private String filename;

    private boolean sortByRelevance;
    private boolean sortByDate;
    private Integer sinceYear;

    public void setSortByRelevance(boolean sortByRelevance) {
        this.sortByRelevance = sortByRelevance;
    }

    public void setSortByDate(boolean sortByDate) {
        this.sortByDate = sortByDate;
    }

    public void setSinceYear(Integer sinceYear) {
        this.sinceYear = sinceYear;
    }

    public void setToYear(Integer toYear) {
        this.toYear = toYear;
    }

    private Integer toYear;

    public QueryEngine(String filename) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        AuthorHandler handler = new AuthorHandler();
        xmlReader.setContentHandler(handler);
        xmlReader.parse(convertToFileURL(filename));

        sortByDate = false;
        sortByRelevance = false;
        sinceYear = 0;
        toYear = 4242;

        this.filename = filename;

        authors = handler.getAuthors();
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
            return sorted(handler.getRet());
        } else {
            System.out.println("Author not found");
            return new ArrayList<>();
        }
    }

    public ArrayList<Publication> query1b(ArrayList<String> keywords) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        Query1bHandler handler = new Query1bHandler(keywords);
        xmlReader.setContentHandler(handler);
        xmlReader.parse(convertToFileURL(this.filename));
        return sorted(handler.getRet());
    }

    public ArrayList<Person> query2(Integer K) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        Query2Handler handler = new Query2Handler(authors);
        xmlReader.setContentHandler(handler);
        xmlReader.parse(convertToFileURL(this.filename));
        ArrayList<Person> ans = new ArrayList<>();
        for (Map.Entry<Person, Integer> cur : handler.getNumPublications().entrySet()) {
            if (cur.getValue() >= K) {
                ans.add(cur.getKey());
            }
        }
        return ans;
    }

    public Integer query3(String authorName, Integer yearUpto) throws ParserConfigurationException, SAXException, IOException {
        Person value;
        if ((value = authors.get(authorName)) != null) {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            Query3Handler handler = new Query3Handler(authors, yearUpto);
            xmlReader.setContentHandler(handler);
            xmlReader.parse(convertToFileURL(this.filename));
            return handler.getNumPublicationsProjected().get(value);
        } else {
            System.out.println("Author does not exist");
            return 0;
        }
    }

    class RelevanceComparator implements Comparator<Pair<Integer, Publication>> {
        @Override
        public int compare(Pair<Integer, Publication> o1, Pair<Integer, Publication> o2) {
            return o1.getFirst().compareTo(o2.getFirst());
        }
    }

    class DateLatestFirstComparator implements Comparator<Pair<Integer, Publication>> {
        @Override
        public int compare(Pair<Integer, Publication> o1, Pair<Integer, Publication> o2) {
            Integer temp = -o1.getSecond().getYear();
            return temp.compareTo(-o2.getSecond().getYear());
        }
    }

    private ArrayList<Publication> sorted(ArrayList<Pair<Integer, Publication>> res) {
        if (sortByDate && sortByRelevance) {
            Collections.sort(res);
        } else if (sortByDate) {
            for (Pair<Integer, Publication> cur : res) {
                cur.setFirst(0);
            }
            Collections.sort(res);
        } else if (sortByRelevance) {
            Collections.sort(res, new RelevanceComparator());
        } else {
            Collections.sort(res, new DateLatestFirstComparator());
        }
        ArrayList<Publication> ans = res.stream().filter(cur -> cur.getSecond().getYear() >= sinceYear &&
                cur.getSecond().getYear() <= toYear).map(Pair::getSecond).collect(Collectors.toCollection(ArrayList::new));
        return ans;
    }
}

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Tester {
    public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException {
        QueryEngine queryEngine = new QueryEngine("dblp.xml");
        System.out.println("Done parsing authors list");
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("Computer");
        keywords.add("Programming");
        queryEngine.setSortByRelevance(true);
        ArrayList<Publication> ans = queryEngine.query1b(keywords);
        for (Publication x : ans) {
            System.out.println(x.getTitle());
        }
    }
}

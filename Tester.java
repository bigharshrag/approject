import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Tester {
    public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException {
        QueryEngine queryEngine = new QueryEngine("dblp.xml");
        System.out.println("Done parsing authors list");

        //Query 1b.
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("Computer");
        keywords.add("Programming");
        queryEngine.setSortByRelevance(true);
        ArrayList<Publication> ans = queryEngine.query1b(keywords);
        for (Publication x : ans) {
            System.out.println(x.getTitle());
        }

        System.out.printf("\n\n\n\n\n\n\n\n");

        //Query 1a
        ans = queryEngine.query1a("Rahul Purandare");
        for (Publication x : ans) {
            System.out.println(x.getTitle());
        }

        System.out.printf("\n\n\n\n\n\n\n\n");

        //Query 2.
        ArrayList<Person> ans2 = queryEngine.query2(900);
        for (Person x : ans2) {
            System.out.println(x.getNames().get(0));
        }
        System.out.println(ans.size());
    }
}

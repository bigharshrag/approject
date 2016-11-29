import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Tester {
    public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException {
        QueryEngine queryEngine = new QueryEngine("dblp.xml");
        System.out.println("Done parsing authors list");

        String author = "Harold Vincent Poor";

        //Query 1a
        ArrayList<Publication> ans = queryEngine.query1a(author);
        System.out.printf("actual size: %d\n", ans.size());

        //Query 3
        System.out.printf("predicted size: %d\n", queryEngine.query3(author, 2013));

    }
}

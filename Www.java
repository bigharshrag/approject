import java.util.ArrayList;
import java.util.Date;

public class Www extends Publication {
    private String title;
    private String url;

    public Www(String key, Date mDate, ArrayList<Person> authors, String title, String url) {
        super(key, mDate, authors);
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}

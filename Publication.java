import java.util.ArrayList;
import java.util.Date;

public class Publication {
    private String key;
    private Date mDate;
    private ArrayList<Person> authors;

    public Publication(String key, Date mDate, ArrayList<Person> authors) {
        this.key = key;
        this.mDate = mDate;
        this.authors = authors;
    }

    public String getKey() {
        return key;
    }
}

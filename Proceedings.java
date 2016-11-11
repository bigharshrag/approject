import java.util.ArrayList;
import java.util.Date;

public class Proceedings extends Publication {
    private ArrayList<Person> editors;
    String title;
    Integer volume;
    Integer year;
    String url;

    public Proceedings(String key, Date mDate, ArrayList<Person> authors, ArrayList<Person> editors, String title, Integer volume, Integer year, String url) {
        super(key, mDate, authors);
        this.editors = editors;
        this.title = title;
        this.volume = volume;
        this.year = year;
        this.url = url;
    }

    public ArrayList<Person> getEditors() {
        return editors;
    }

    public String getTitle() {
        return title;
    }

    public Integer getVolume() {
        return volume;
    }

    public Integer getYear() {
        return year;
    }

    public String getUrl() {
        return url;
    }
}

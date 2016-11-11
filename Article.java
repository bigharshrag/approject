import java.util.ArrayList;
import java.util.Date;

public class Article extends Publication {
    private String title;
    private Pair<Integer> pages;
    private Integer year;
    private Integer volume;
    private String journal;
    private Integer number;
    private String ee;
    private String url;

    public Article(String key, Date mDate, ArrayList<Person> authors, String title, Pair<Integer> pages, Integer year, Integer volume, String journal, Integer number, String ee, String url) {
        super(key, mDate, authors);
        this.title = title;
        this.pages = pages;
        this.year = year;
        this.volume = volume;
        this.journal = journal;
        this.number = number;
        this.ee = ee;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public Pair<Integer> getPages() {
        return pages;
    }

    public Integer getYear() {
        return year;
    }
}

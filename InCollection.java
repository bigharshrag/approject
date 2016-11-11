import java.util.ArrayList;
import java.util.Date;

public class InCollection extends Publication {
    private String title;
    private Pair<Integer> pages;
    private String crossref;
    private Integer year;
    private String bookTitle;
    private String url;

    public InCollection(String key, Date mDate, ArrayList<Person> authors, String title, Pair<Integer> pages, String crossref, Integer year, String bookTitle, String url) {
        super(key, mDate, authors);
        this.title = title;
        this.pages = pages;
        this.crossref = crossref;
        this.year = year;
        this.bookTitle = bookTitle;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public Pair<Integer> getPages() {
        return pages;
    }

    public String getCrossref() {
        return crossref;
    }

    public Integer getYear() {
        return year;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getUrl() {
        return url;
    }
}

import java.util.ArrayList;
import java.util.Date;

public class Book extends Publication {
    private String title;
    private Integer pages;
    private Integer year;
    private String publisher;

    public Book(String key, Date mDate, ArrayList<Person> authors, String title, Integer pages, Integer year, String publisher) {
        super(key, mDate, authors);
        this.title = title;
        this.pages = pages;
        this.year = year;
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPages() {
        return pages;
    }

    public Integer getYear() {
        return year;
    }

    public String getPublisher() {
        return publisher;
    }
}

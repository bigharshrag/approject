import java.util.ArrayList;
import java.util.Date;

public class PhdThesis extends Publication {
    private String title;
    private Pair<Integer> pages;
    private String school;
    private Integer year;

    public String getTitle() {
        return title;
    }

    public Pair<Integer> getPages() {
        return pages;
    }

    public String getSchool() {
        return school;
    }

    public Integer getYear() {
        return year;
    }

    public PhdThesis(String key, Date mDate, ArrayList<Person> authors, String title, Pair<Integer> pages, String school, Integer year) {
        super(key, mDate, authors);
        this.title = title;
        this.pages = pages;
        this.school = school;
        this.year = year;
    }
}

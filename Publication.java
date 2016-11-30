/**
@author Parth Mittal (2015069)
@author Rishabh Garg (2015076)
*/
import java.util.ArrayList;
import java.util.Date;

public class Publication implements Comparable<Publication> {
    private String key;
    private Date mDate;
    private ArrayList<String> authors;

    private String title;
    private String pages;
    private Integer year;
    private String volume;
    private String journal;
    private String number;
    private String ee;
    private String url;
    private String publisher;
    private String crossref;
    private String bookTitle;
    private String school;
    private ArrayList<String> editors;
    private String publicationType;

    public void addEditor(String editor) {
        editors.add(editor);
    }

    public void addAuthor(String author) {
        authors.add(author);
    }

    public String getNumber() {
        return number;
    }

    public String getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(String publicationType) {
        this.publicationType = publicationType;
    }

    public Publication(String publicationType) {
        this.publicationType = publicationType;
        authors = new ArrayList<>();
        editors = new ArrayList<>();
    }

    public Date getmDate() {
        return mDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getEe() {
        return ee;
    }

    public void setEe(String ee) {
        this.ee = ee;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCrossref() {
        return crossref;
    }

    public void setCrossref(String crossref) {
        this.crossref = crossref;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getKey() {
        return key;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public ArrayList<String> getEditors() {
        return editors;
    }

    public void setEditors(ArrayList<String> editors) {
        this.editors = editors;
    }

    @Override
    public int compareTo(Publication o) {
        return this.year.compareTo(o.getYear());
    }
}

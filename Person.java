import java.util.ArrayList;

public class Person {
    private String name;
    private ArrayList<Publication> publications;

    public Person(String name, ArrayList<Publication> publications) {
        this.name = name;
        this.publications = publications;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Publication> getPublications() {
        return publications;
    }
}

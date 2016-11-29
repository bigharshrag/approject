import java.util.ArrayList;

public class Person {
    private ArrayList<String> names;
    private ArrayList<Publication> publications;

    public Person() {
        names = new ArrayList<>();
        publications = new ArrayList<>();
    }

    public void addName(String name) {
        names.add(name);
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Publication> getPublications() {
        return publications;
    }
}

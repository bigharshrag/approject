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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return names.equals(person.names);

    }

    @Override
    public int hashCode() {
        return names.hashCode();
    }

    public ArrayList<Publication> getPublications() {
        return publications;
    }
}

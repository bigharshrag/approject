import org.xml.sax.*;
import org.xml.sax.helpers.*;

import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class AuthorHandler extends DefaultHandler {
    private HashMap<String, Person> authors;
    private ArrayList<Person> authorsList;
    private boolean store;
    private String buffer;
    private Person currentPerson;

    public void startDocument() throws SAXException {
        authors = new HashMap<>();
        authorsList = new ArrayList<>();
        store = false;
        buffer = new String();
        currentPerson = null;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(localName.equals("www") && attributes.getValue(0).startsWith("homepages")) {
            store = true;
            currentPerson = new Person();
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("www") && store) {
            for (String name : currentPerson.getNames()) {
                authors.put(name, currentPerson);
            }
            authorsList.add(currentPerson);
            store = false;
        } else if (store) {
            if (currentPerson == null) {
                throw new SAXException("We want to store, but no object has been created");
            } else {
                switch(localName) {
                    case "author":
                        currentPerson.addName(buffer);
                        break;
                    default:
                        break;
                }
            }
        }
        buffer = new String();
    }

    public HashMap<String, Person> getAuthors() {
        return authors;
    }

    public ArrayList<Person> getAuthorsList() {
        return authorsList;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (store) {
            buffer += new String(ch, start, length);
        }
    }
}

package drose379.kairos;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String name;
    private String description;
    private List<String> subjects = new ArrayList<String>();

    public Category(String name,String description) {
        this.name = name;
        this.description = description;
    }

    public void addSubject(String subject) {
        subjects.add(subject);
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public List<String> getSubjects() {
        return subjects;
    }
    public String getSubject(int position) {
        return subjects.get(position);
    }

}
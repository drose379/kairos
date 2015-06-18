package drose379.kairos;

/**
 * Created by drose379 on 6/17/15.
 */
public class Subject {
    private String name;
    private String category;

    public Subject(String name,String category) {
        this.name = name;
        this.category = category;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
}

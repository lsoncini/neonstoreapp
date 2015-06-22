package model;

/**
 * Created by Tomi on 22/6/15.
 */
public class Subcategory extends Model {

    public int id;
    public String name;

    public Subcategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String inspect() {
        return "<Category " + id + ": " + name + ">";
    }
}

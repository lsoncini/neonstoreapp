package model;

public class Category extends Model {
    public int id;
    public String name;

    public Category(int id, String name) {
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

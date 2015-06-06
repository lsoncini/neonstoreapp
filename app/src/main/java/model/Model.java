package model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Model {

    public String toString() {
        try {
            String s = "<" + this.getClass().getSimpleName() + "\n";

            for (Field field : this.getClass().getDeclaredFields()) {
                if (! Modifier.isPublic(field.getModifiers())) continue;

                String name  = field.getName();
                Object value = field.get(this);

                s += "\t" + String.format("%-12s", name) + value + "\n";
            }

            return s + ">";

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

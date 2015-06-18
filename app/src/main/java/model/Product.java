package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import api.response.APIAttribute;

public class Product extends Model {

    public static final int
        GENDER   = 1,
        AGE      = 2,
        COLOR    = 4,
        IS_OFFER = 5,
        IS_NEW   = 6
    ;

    public enum Gender {
        Masculino, Femenino;
    }

    public enum Age {
        Adulto, Infantil, Bebe;
    }

    public enum Color {
        Azul,
        Beige,
        Blanco,
        Bordo,
        Carey,
        Celeste,
        Chocolate,
        Coral,
        Dorado,
        Fucsia,
        Gris,
        Marron,
        Multicolor,
        Naranja,
        Natural,
        Negro,
        Oro,
        Plata,
        Rojo,
        Rosa,
        Suela,
        Verde,
        Violeta;
    }


    public int id;
    public String name;
    public int price;
    public Category category;

    public Age age;
    public Gender gender;
    public Color color;
    public List<String> sizes;
    public String brand;
    public String material;

    public Boolean is_new;
    public Boolean is_offer;

    @SerializedName("imageUrl")
    public String images[];

    public List<APIAttribute> attributes;
}

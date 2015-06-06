package model;

import com.google.gson.annotations.SerializedName;

public class Product {

    public int id;
    public String name;
    public Category category;

    @SerializedName("imageUrl")
    public String images[];
}

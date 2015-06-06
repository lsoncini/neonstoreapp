package model;

import com.google.gson.annotations.SerializedName;

public class Product {

    public int id;
    public String name;
    public int price;
    public Category category;

    @SerializedName("imageUrl")
    public String images[];

}

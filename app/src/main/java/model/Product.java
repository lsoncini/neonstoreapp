package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import api.response.APIAttribute;

public class Product extends Model {

    public int id;
    public String name;
    public int price;
    public Category category;

    public String color;
    public List<String> sizes;
    public String brand;
    public String material;

    public Boolean is_new;
    public Boolean is_offer;

    @SerializedName("imageUrl")
    public String images[];

    public List<APIAttribute> attributes;
}

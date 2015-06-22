package model;

public class OrderItem {

    public static class OrderItemProduct {
        public int id;
        public String name;
        public String imageUrl;
    }

    public int id;

    public OrderItemProduct product;
    public int quantity;
    public int price;
}

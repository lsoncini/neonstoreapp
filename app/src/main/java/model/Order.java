package model;

public class Order {
    public static final String
        CREATED   = "1",
        CONFIRMED = "2",
        SHIPPED   = "3",
        DELIVERED = "4"
    ;

    public int id;
    public Address address;

    public String status;

    public String receivedDate;
    public String processedDate;
    public String shippedDate;
    public String deliveredDate;

    public double latitude;
    public double longitude;
}

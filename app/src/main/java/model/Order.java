package model;

import java.util.Calendar;
import java.util.Date;

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

    public Calendar receivedDate;
    public Calendar processedDate;
    public Calendar shippedDate;
    public Calendar deliveredDate;

    public Double latitude;
    public Double longitude;
}



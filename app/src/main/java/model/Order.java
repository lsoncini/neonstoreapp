package model;

import java.util.Date;

public class Order extends Model {
    public static final String
        CREATED   = "1",
        PROCESSED = "2",
        SHIPPED   = "3",
        DELIVERED = "4"
    ;

    public int id;
    public Address address;

    public String status;

    public Date receivedDate;
    public Date processedDate;
    public Date shippedDate;
    public Date deliveredDate;

    public double latitude;
    public double longitude;

    public boolean hasChangedSince(Date when) {
        return (
            isAfter(receivedDate, when) ||
            isAfter(processedDate, when) ||
            isAfter(shippedDate, when) ||
            isAfter(deliveredDate, when)
        );
    }

    private boolean isAfter(Date a, Date b) {
        return a != null && a.getTime() > b.getTime();
    }
}

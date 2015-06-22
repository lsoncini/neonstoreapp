package model;

import java.util.Calendar;
import java.util.List;

public class Order extends Model {
    public static final String
        CREATED   = "1",
        PROCESSED = "2",
        SHIPPED   = "3",
        DELIVERED = "4"
    ;

    public Integer id;
    public Address address;

    public String status;

    public Calendar receivedDate;
    public Calendar processedDate;
    public Calendar shippedDate;
    public Calendar deliveredDate;

    public Double latitude;
    public Double longitude;

    List<OrderItem> items;

    public boolean hasChangedSince(Calendar when) {
        return (
            isAfter(receivedDate, when) ||
            isAfter(processedDate, when) ||
            isAfter(shippedDate, when) ||
            isAfter(deliveredDate, when)
        );
    }

    private boolean isAfter(Calendar a, Calendar b) {
        return a != null && a.after(b);
    }
}



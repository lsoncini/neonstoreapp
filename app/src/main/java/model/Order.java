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

    public int timehash() {
        int result = receivedDate != null ? receivedDate.hashCode() : 0;
        result = 31 * result + (processedDate != null ? processedDate.hashCode() : 0);
        result = 31 * result + (shippedDate != null ? shippedDate.hashCode() : 0);
        result = 31 * result + (deliveredDate != null ? deliveredDate.hashCode() : 0);
        return result;
    }
}



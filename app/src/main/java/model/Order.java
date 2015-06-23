package model;

import android.content.Context;

import com.neon.neonstore.R;

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

    public List<OrderItem> items;

    public Order() {}

    public Order(Integer id) {
        this.id = id;
    }

    public int timehash() {
        int result = receivedDate != null ? receivedDate.hashCode() : 0;
        result = 31 * result + (processedDate != null ? processedDate.hashCode() : 0);
        result = 31 * result + (shippedDate != null ? shippedDate.hashCode() : 0);
        result = 31 * result + (deliveredDate != null ? deliveredDate.hashCode() : 0);
        return result;
    }

    public Integer total() {
        int total = 0;

        for (OrderItem item: items)
            total += item.price * item.quantity;

        return total;
    }

    public String getStatusString(Context c) {
        switch(status) {
            case Order.CREATED  : return c.getString(R.string.order_created);
            case Order.PROCESSED: return c.getString(R.string.order_processed);
            case Order.SHIPPED  : return c.getString(R.string.order_shipped);
            case Order.DELIVERED: return c.getString(R.string.order_delivered);
        }

        return null;
    }
}



package notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.neon.neonstore.R;

import activity.MainActivity;
import model.Order;

public class OrderStatusNotification {

    public static final int CLICKED = 123;

    private Order order;


    public OrderStatusNotification(Order order) {
        this.order = order;
    }

    public void show(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
            context,
            CLICKED,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );

        Notification notification = new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_cart)
            .setContentTitle(getTitle(context))
            .setContentText(getText(context))
            .setContentIntent(pendingIntent)
        .build();

        NotificationManager nm = (NotificationManager) context.getSystemService(
            Context.NOTIFICATION_SERVICE
        );

        nm.notify(order.id, notification);
    }

    private String getTitle(Context c) {
        return c.getString(R.string.order) + " #" + order.id;
    }

    private String getText(Context c) {
        switch(order.status) {
            case Order.CREATED  : return c.getString(R.string.order_created);
            case Order.PROCESSED: return c.getString(R.string.order_processed);
            case Order.SHIPPED  : return c.getString(R.string.order_shipped);
            case Order.DELIVERED: return c.getString(R.string.order_delivered);
        }

        return null;
    }
}
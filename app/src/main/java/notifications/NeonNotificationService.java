package notifications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.HashMap;
import java.util.List;

import api.response.OrderListResponse;
import model.Order;
import store.Store;

public class NeonNotificationService extends Service implements Runnable {
    private String username;
    private String authToken;

    private HashMap<Integer, Integer> orderIdToTimehash;

    private boolean stopped = false;

    @Override
    public void onCreate() {
        super.onCreate();
        start();
    }

    public void run() {
        System.out.println("Running Notification Service in Thread " + Thread.currentThread());

        while (! stopped) {
            checkOrders();
            SystemClock.sleep(5000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        synchronized(this) {
            username  = intent.getStringExtra("username");
            authToken = intent.getStringExtra("authenticationToken");
        }

        return START_NOT_STICKY;
    }

    private void checkOrders() {
        System.out.println("Checking orders");
        if (username == null) return; // safe to call at the wrong time

        final String currentUsername, currentAuthToken;

        synchronized (this) {
            currentUsername = username;
            currentAuthToken = authToken;
        }

        System.out.println("Checking orders " + username + " : " + authToken);
        OrderListResponse res = Store.api.getAllOrders(currentUsername, currentAuthToken);

        synchronized (this) {
            if (currentUsername != username)
                return;

            System.out.println("Has " + res.orders.size() + " orders");
            processOrders(res.orders);
        }
    }

    private void processOrders(List<Order> orders) {
        if (orderIdToTimehash == null) {
            orderIdToTimehash = new HashMap<>();

            for (Order o: orders)
                orderIdToTimehash.put(o.id, o.timehash());

        } else {
            for (Order order: orders) {
                Integer previousTimehash = orderIdToTimehash.get(order.id);

                if (previousTimehash != null && order.timehash() == previousTimehash)
                    continue;

                orderIdToTimehash.put(order.id, order.timehash());
                new OrderStatusNotification(order).show(getApplicationContext());
            }
        }
    }

    void start() {
        new Thread(this).start();
    }

    void stop() {
        stopped = true;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

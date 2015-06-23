package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.neon.neonstore.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Order;

public class OrderListItem extends FrameLayout{
    @InjectView(R.id.order_id) TextView id;
    @InjectView(R.id.order_status) TextView status;
    @InjectView(R.id.order_address) TextView address;
    @InjectView(R.id.order_process_date) TextView process_date;
    @InjectView(R.id.order_received_date) TextView received_date;
    @InjectView(R.id.order_total) TextView total;

    public OrderListItem(Context context) {
        super(context);
        init();
    }

    public OrderListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OrderListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        addView(inflate(getContext(), R.layout.order_list_item, null));
        ButterKnife.inject(this);
    }

    void setOrder(Order order) {
        id.setText(R.string.order_id + order.id);
        status.setText(R.string.order_status + order.getStatusString(getContext()));
        address.setText(R.string.order_address + order.address.name);
        process_date.setText(R.string.order_process_date + order.processedDate.toString());
        received_date.setText(R.string.order_received_date + order.receivedDate.toString());
        total.setText(R.string.order_total + "$" + String.valueOf(order.total()));
    }
}

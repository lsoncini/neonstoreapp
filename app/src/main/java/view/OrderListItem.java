package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.neon.neonstore.R;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Order;

public class OrderListItem extends FrameLayout {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @InjectView(R.id.order_id) TextView id;
    @InjectView(R.id.order_status) TextView status;
    @InjectView(R.id.order_address) TextView address;
    @InjectView(R.id.order_process_date) TextView process_date;
    @InjectView(R.id.order_received_date) TextView received_date;

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
        Context c = getContext();

        id.setText(c.getString(R.string.order_id) + order.id);
        status.setText(c.getString(R.string.order_status) + order.getStatusString(getContext()));
        address.setText(c.getString(R.string.order_address) + order.address.name);
        process_date.setText(c.getString(R.string.order_process_date) + dateFormat.format(order.processedDate.getTime()));
        received_date.setText(c.getString(R.string.order_received_date) + dateFormat.format(order.receivedDate.getTime()));
    }
}

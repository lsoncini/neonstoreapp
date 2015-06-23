package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;

import com.neon.neonstore.R;

import java.util.List;

import adapter.OrderAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Order;
import model.Product;

public class OrderList extends FrameLayout{
    public interface OrderListListener {
        void onOrderSelected(Order order);
    }

    @InjectView(R.id.list)
    ListView list;

    OrderAdapter adapter;
    OrderListListener listener;


    public OrderList(Context context) {
        super(context);
        init();
    }

    public OrderList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OrderList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addView(inflate(getContext(), R.layout.order_list, null));
        ButterKnife.inject(this);

        list.setFocusable(false);

        list.setAdapter(adapter = new OrderAdapter(getContext()));
        list.setOnItemClickListener(onListItemClick);
    }

    public void clear() {
        adapter.clear();
    }

    public void addProducts(List<Order> orders) {
        adapter.addAll(orders);
    }

    public void setOrders(List<Order> orders) {
        adapter.clear();
        adapter.addAll(orders);
    }

    public void setListener(OrderListListener listener) {
        this.listener = listener;
    }


    private final AdapterView.OnItemClickListener onListItemClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (listener != null)
                listener.onOrderSelected(adapter.getItem(position));
        }
    };


    public static class OrderAdapter extends ArrayAdapter<Order> {

        public OrderAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OrderListItem view = (convertView != null) ?
                    (OrderListItem) convertView :
                    new OrderListItem(getContext())
                    ;

            view.setOrder(getItem(position));

            return view;
        }
    }
}

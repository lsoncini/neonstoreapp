package activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.neon.neonstore.R;

import java.text.SimpleDateFormat;

import api.APIBack;
import api.response.OrderResponse;
import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Order;
import model.OrderItem;
import store.Store;
import view.OrderItemListItem;

public class OrderDetailFragment extends NeonFragment {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Store store = Store.getInstance();

    public String getTitle() {
        return "Order Details";
    }

    @InjectView(R.id.orderId)      TextView orderId;
    @InjectView(R.id.addressName)  TextView addressName;
    @InjectView(R.id.status)       TextView status;
    @InjectView(R.id.total)        TextView total;
    @InjectView(R.id.receivedDate) TextView receivedDate;

    @InjectView(R.id.shippedDateGroup) ViewGroup shippedDateGroup;
    @InjectView(R.id.shippedDate)      TextView  shippedDate;

    @InjectView(R.id.deliveredDateGroup) ViewGroup deliveredDateGroup;
    @InjectView(R.id.deliveredDate)      TextView  deliveredDate;

    @InjectView(R.id.items) ListView items;
    OrderItemAdapter itemAdapter;

    public Order order;
    public ListView productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ButterKnife.inject(this, view);

        items.setAdapter(itemAdapter = new OrderItemAdapter(getActivity()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateView();
    }

    public OrderDetailFragment setOrder(Order order) {
        this.order = order;
        updateView();
        return this;
    }

    private void updateView() {
        if (getView() == null) return;
        showSpinner();

        store.fetchOrder(order.id, new APIBack<OrderResponse>() {
            @Override
            public void onSuccess(OrderResponse res) {
                loadFullOrder(res.order);
                hideSpinner();
            }
        });
    }

    private void loadFullOrder(Order order) {
        this.order = order;
        System.out.println(order.inspect());

        orderId.setText("#" + order.id);
        addressName.setText(order.address.name);
        status.setText(order.getStatusString(getActivity()));

        receivedDate.setText(dateFormat.format(order.receivedDate.getTime()));

        if (order.shippedDate != null) {
            shippedDateGroup.setVisibility(View.VISIBLE);
            shippedDate.setText(dateFormat.format(order.shippedDate.getTime()));
        } else
            shippedDateGroup.setVisibility(View.GONE);

        if (order.deliveredDate != null) {
            deliveredDateGroup.setVisibility(View.VISIBLE);
            deliveredDate.setText(dateFormat.format(order.deliveredDate.getTime()));
        } else
            deliveredDateGroup.setVisibility(View.GONE);

        total.setText("$" + order.total());

        itemAdapter.clear();
        itemAdapter.addAll(order.items);
    }

    public static class OrderItemAdapter extends ArrayAdapter<OrderItem> {

        public OrderItemAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OrderItemListItem view = (convertView != null) ?
                (OrderItemListItem) convertView :
                new OrderItemListItem(getContext())
                ;

            view.setOrderItem(getItem(position));

            return view;
        }
    }
}

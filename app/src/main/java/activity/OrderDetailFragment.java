package activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.neon.neonstore.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Order;

public class OrderDetailFragment extends Fragment{
    /*
    @InjectView(R.id.addressName)
    TextView addressName;

    @InjectView(R.id.status)
    TextView status;

    @InjectView(R.id.receivedDate)
    TextView receivedDate;

    @InjectView(R.id.shippedDate)
    TextView shippedDate;

    public Order order;
    public ListView products;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ButterKnife.inject(this, view);

        products.setAdapter();

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

        addressName.setText(addressName.toString());
        status.setText(status.toString());
        receivedDate.setText(order.receivedDate.toString());
        shippedDate.setText(order.shippedDate.toString());
    } */
}

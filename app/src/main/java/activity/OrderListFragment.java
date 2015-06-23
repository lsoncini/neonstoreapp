package activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neon.neonstore.R;

import api.APIBack;
import api.response.APIError;
import api.response.OrderListResponse;
import butterknife.ButterKnife;
import butterknife.InjectView;
import store.Store;
import view.OrderList;

public class OrderListFragment extends NeonFragment {

    final Store store = Store.getInstance();

    @InjectView(R.id.orderList)
    OrderList orderList;

    @Override
    public String getTitle() {
        return getResources().getString(R.string.title_orders);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        ButterKnife.inject(this, view);

        orderList.setListener((OrderList.OrderListListener) getActivity());

        return view;
    }

    public void updateView() {
        if (getView() == null) return;

        showSpinner();

        store.fetchOrders(new APIBack<OrderListResponse>() {
            public void onSuccess(OrderListResponse res) {
                hideSpinner();
                orderList.setOrders(res.orders);
            }

            public void onError(APIError err) {
                hideSpinner();
                System.err.println(err);
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }
}

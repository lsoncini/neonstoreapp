package activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neon.neonstore.R;

import api.ProductListResponse;
import application.Neon;
import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import view.ProductGrid;


public class HomeFragment extends Fragment {

    @InjectView(R.id.productGrid) ProductGrid productGrid;

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);

        Neon.getAPI().getProductsByCategory(1, new Callback<ProductListResponse>() {
            public void success(ProductListResponse res, Response response) {
                productGrid.setProducts(res.products);
            }

            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
package activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neon.neonstore.R;

import api.APIBack;
import api.APIQuery;
import api.Store;
import api.response.APIError;
import api.response.ProductListResponse;
import butterknife.ButterKnife;
import butterknife.InjectView;
import view.ProductGrid;
import view.ProductGrid.ProductGridListener;


public class HomeFragment extends Fragment {

    @InjectView(R.id.productGrid) ProductGrid productGrid;


    final Store store = Store.getInstance();


    public HomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);

        productGrid.setListener((ProductGridListener) getActivity());

        APIQuery query = new APIQuery()
            .category(store.getCategories().get(0))
            .page(1, 8)
            .orderBy(APIQuery.BY_NAME, APIQuery.ASC)
        ;

        APIBack<ProductListResponse> apiBack =  new APIBack<ProductListResponse>() {
            public void onSuccess(ProductListResponse res) {
                productGrid.setProducts(res.products);
            }

            public void onError(APIError err) {
                System.err.println(err);
            }
        };

        store.searchProducts(query, apiBack);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        productGrid.setListener(null);
    }
}
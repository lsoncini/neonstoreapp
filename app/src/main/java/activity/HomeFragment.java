package activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.neon.neonstore.R;

import api.APIBack;
import api.APIQuery;
import api.response.APIError;
import api.response.ProductListResponse;
import butterknife.ButterKnife;
import butterknife.InjectView;
import store.Store;
import view.ProductGrid;
import view.ProductGrid.ProductGridListener;


public class HomeFragment extends NeonFragment {

    final Store store = Store.getInstance();

    @InjectView(R.id.productGrid) ProductGrid productGrid;


    @Override
    public String getTitle() {
        return "Neon";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView view = (ScrollView)inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);

        productGrid.setListener((ProductGridListener) getActivity());

        APIQuery query = new APIQuery()
//            .whereCategory(store.getCategories().get(0))
//            .whereColor(Color.Blanco)
            .whereName("Pancha")
            .page(1, 8)
            .orderBy(APIQuery.BY_NAME, APIQuery.ASC)
        ;

        showSpinner();

        store.searchProducts(query, new APIBack<ProductListResponse>() {
            public void onSuccess(ProductListResponse res) {
                hideSpinner();
                productGrid.setProducts(res.products);
            }

            public void onError(APIError err) {
                System.err.println(err);
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(productGrid != null) {
            productGrid.setListener(null);
        }
    }
}
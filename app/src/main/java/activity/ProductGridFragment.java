package activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neon.neonstore.R;

import api.APIBack;
import api.APIQuery;
import store.Store;
import api.response.APIError;
import api.response.ProductListResponse;
import butterknife.ButterKnife;
import butterknife.InjectView;
import view.ProductGrid;
import view.ProductGrid.ProductGridListener;

public class ProductGridFragment extends NeonFragment {

    final Store store = Store.getInstance();

    @InjectView(R.id.productGrid) ProductGrid productGrid;

    // The items currently displayed in the grid were fetched using this query:
    APIQuery query;
    boolean queryChanged;


    @Override
    public String getTitle() {
        return getResources().getString(R.string.title_result);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_grid, container, false);
        ButterKnife.inject(this, view);

        productGrid.setListener((ProductGridListener) getActivity());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateView();
    }


    public ProductGridFragment setQuery(APIQuery query) {
        this.query = query;
        queryChanged = true;
        updateView();
        return this;
    }

    public void updateView() {
        if (getView() == null || !queryChanged) return;

        productGrid.clear();
        queryChanged = false;

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
    }
}

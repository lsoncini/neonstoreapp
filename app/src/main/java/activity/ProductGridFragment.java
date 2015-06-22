package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.neon.neonstore.R;

import java.util.List;

import api.APIBack;
import api.APIQuery;
import model.Product;
import store.Store;
import api.response.APIError;
import api.response.ProductListResponse;
import butterknife.ButterKnife;
import butterknife.InjectView;
import view.ProductGrid;

public class ProductGridFragment extends NeonFragment {

    final Store store = Store.getInstance();

    @InjectView(R.id.productGrid)
    ProductGrid productGrid;
    @InjectView(R.id.filter_button)
    Button filterButton;
    @InjectView(R.id.order_button) Button sortButton;

    // The items currently displayed in the grid were fetched using this query:
    APIQuery query;
    boolean queryChanged;
    int sortIndex=0;


    @Override
    public String getTitle() {
        return getResources().getString(R.string.title_result);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_grid, container, false);
        ButterKnife.inject(this, view);

        productGrid.setListener((ProductGrid.ProductGridListener) getActivity());
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());

                // set title
                alertDialogBuilder.setTitle(R.string.sort);

                // set dialog message
                alertDialogBuilder
                        .setSingleChoiceItems(R.array.sort_options, sortIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int index) {
                                sortIndex = index;
                            }
                        })
                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                switch (sortIndex) {
                                    case 0:
                                        query.orderBy(APIQuery.BY_PRICE, APIQuery.ASC);
                                        break;
                                    case 1:
                                        query.orderBy(APIQuery.BY_PRICE, APIQuery.DESC);
                                        break;
                                    default:
                                        break;
                                }
                                setQuery(query);
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

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
                hideSpinner();
                System.err.println(err);
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setQuery(query);
    }
}

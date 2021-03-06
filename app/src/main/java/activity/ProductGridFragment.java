package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.neon.neonstore.R;

import java.util.List;

import api.APIBack;
import api.APIQuery;
import api.response.SubcategoryListResponse;
import model.Category;
import model.Product;
import model.Subcategory;
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
    @InjectView(R.id.subcategories_button)
    Button subcategoriesButton;
    @InjectView(R.id.order_button) Button sortButton;
    @InjectView(R.id.emptyViewText)
    TextView emptyTextView;

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

    private void emptyViewMessage(boolean show){
        if (show) emptyTextView.setVisibility(View.VISIBLE);
        else emptyTextView.setVisibility(View.GONE);
    }

    public void updateView() {
        if (getView() == null || !queryChanged) return;

        productGrid.clear();
        queryChanged = false;

        showSpinner();
        emptyViewMessage(false);

        store.searchProducts(query, new APIBack<ProductListResponse>() {

            public void onSuccess(ProductListResponse res) {
                hideSpinner();
                if(res.products.size()>0)
                    emptyViewMessage(false);
                else emptyViewMessage(true);

                productGrid.setProducts(res.products);
            }

            public void onError(APIError err) {
                hideSpinner();
                System.err.println(err);
            }

        });

        APIQuery subQuery = new APIQuery()
                .whereCategory(query.category);

        subQuery.filters = query.filters;

        if(query.category != null) {

            store.fetchSubcategories(subQuery, new APIBack<SubcategoryListResponse>() {
                public void onSuccess(SubcategoryListResponse res) {
                    configureDialogForSubcategories(res.subcategories);

                }

                public void onError(APIError err) {
                    System.err.println(err);
                }
            });
        }
    }

    private void configureDialogForSubcategories(final List<Subcategory> subcategories){
        subcategoriesButton.setEnabled(true);

        final CharSequence[] titles = new CharSequence[subcategories.size()+1];

        int i = 0;

        for(i=0; i<subcategories.size();i++){
            titles[i] = subcategories.get(i).name;
        }

        titles[i] = getResources().getString(R.string.none);

        subcategoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());

                // set title
                alertDialogBuilder.setTitle(R.string.subcategories);

                // set dialog message
                alertDialogBuilder
                        .setSingleChoiceItems(titles, sortIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int index) {
                                sortIndex = index;
                            }
                        })
                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if(sortIndex == subcategories.size()) {
                                    query.subcategory = null;
                                    subcategoriesButton.setText(getResources().getString(R.string.subcategories));
                                }
                                else {
                                    query.subcategory = subcategories.get(sortIndex);
                                    subcategoriesButton.setText(subcategories.get(sortIndex).name);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        setQuery(query);
    }
}

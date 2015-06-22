package activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.neon.neonstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import api.APIBack;
import api.response.APIError;
import api.response.ProductResponse;
import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Product;
import store.Store;

public class ProductDetailFragment extends NeonFragment {

    @InjectView(R.id.name)  TextView name;
    @InjectView(R.id.price) TextView price;
    @InjectView(R.id.image) ImageView image;
    @InjectView(R.id.brand) TextView brand;
    @InjectView(R.id.colors) Spinner colors;
    @InjectView(R.id.sizes) Spinner sizes;

    public Product product;
    final Store store = Store.getInstance();
    boolean productChanged;


    @Override
    public String getTitle() {
        return getResources().getString(R.string.title_product);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateView();
    }

    public ProductDetailFragment setProduct(Product product) {
        this.product = product;
        productChanged = true;
        updateView();
        return this;
    }

    private void updateView() {
        if (getView() == null || !productChanged) return;

        if(product == null) return;

        name.setText(product.name);
        price.setText("$" + product.price);
        brand.setText(product.brand);
        productChanged = false;


        showSpinner();

        store.fetchProduct(product.id,new APIBack<ProductResponse>(){
            public void onSuccess(ProductResponse res) {
                hideSpinner();
                name.setText(res.product.name);
                price.setText("$" + res.product.price);
                brand.setText(res.product.brand);

                ArrayAdapter<String> colorsAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_item, res.product.color);
                if(colorsAdapter != null) {
                    colors.setAdapter(colorsAdapter);
                }

                if(res.product.sizes == null){
                    res.product.sizes = new ArrayList<String>();
                    res.product.sizes.add(getResources().getString(R.string.text_only_size));
                }

                ArrayAdapter<String> sizesAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_item, res.product.sizes);
                sizes.setAdapter(sizesAdapter);
            }

            public void onError(APIError err) {
                System.err.println(err);
            }

        });

        Picasso.with(getActivity())
            .load(product.images[0])
            .into(image)
        ;
    }
}

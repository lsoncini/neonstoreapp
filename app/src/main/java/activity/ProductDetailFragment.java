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

import java.util.Arrays;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Product;

public class ProductDetailFragment extends NeonFragment {

    @InjectView(R.id.name)  TextView name;
    @InjectView(R.id.price) TextView price;
    @InjectView(R.id.image) ImageView image;
    @InjectView(R.id.brand) TextView brand;
    @InjectView(R.id.colors) Spinner colors;
    @InjectView(R.id.sizes) Spinner sizes;

    public Product product;


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
        updateView();
        return this;
    }

    private void updateView() {
        if (getView() == null) return;

        name.setText(product.name);
        price.setText("$" + product.price);
        brand.setText(product.brand);

        String[] colorsStr = {"Azul", "Rojo", "Verde"};
        String[] sizesStr = {"S", "M", "L", "XL"};

        ArrayAdapter<String> colorsAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, colorsStr);
        colors.setAdapter(colorsAdapter);
        ArrayAdapter<String> sizesAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, sizesStr);
        sizes.setAdapter(sizesAdapter);

        Picasso.with(getActivity())
            .load(product.images[0])
            .into(image)
        ;
    }
}

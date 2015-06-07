package activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neon.neonstore.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Product;

public class ProductDetailFragment extends Fragment {

    @InjectView(R.id.name)  TextView name;
    @InjectView(R.id.price) TextView price;
    @InjectView(R.id.image) ImageView image;

    public Product product;

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

        Picasso.with(getActivity())
            .load(product.images[0])
            .into(image)
        ;
    }
}

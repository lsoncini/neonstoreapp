package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.neon.neonstore.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Product;

public class ProductGridItem extends FrameLayout {

    @InjectView(R.id.name)  TextView name;
    @InjectView(R.id.price) TextView price;
    @InjectView(R.id.image) ImageView image;
    @InjectView(R.id.brand) TextView brand;

    public ProductGridItem(Context context) {
        super(context);
        init();
    }

    public ProductGridItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProductGridItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        addView(inflate(getContext(), R.layout.product_grid_item, null));
        ButterKnife.inject(this);
    }

    void setProduct(Product product) {
        name.setText(product.name);
        price.setText("$" + product.price);
        brand.setText(product.brand);

        Picasso.with(getContext())
            .load(product.images[0])
//            .resize(50, 50)
//            .centerCrop()
            .into(image)
        ;
    }
}

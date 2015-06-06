package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.neon.neonstore.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Product;

public class ProductGrid extends FrameLayout {

    @InjectView(R.id.grid) GridView grid;

    ProductAdapter adapter;


    public ProductGrid(Context context) {
        super(context);
        init();
    }

    public ProductGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProductGrid(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addView(inflate(getContext(), R.layout.product_grid, null));
        ButterKnife.inject(this);

        grid.setAdapter(adapter = new ProductAdapter(getContext()));
    }

    public void setProducts(List<Product> products) {
        adapter.clear();
        adapter.addAll(products);
    }

    public static class ProductAdapter extends ArrayAdapter<Product> {

        public ProductAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductGridItem view = (convertView != null) ?
                (ProductGridItem) convertView :
                new ProductGridItem(getContext())
            ;

            view.setProduct(getItem(position));

            return view;
        }
    }
}

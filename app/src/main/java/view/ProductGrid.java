package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.neon.neonstore.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Product;

public class ProductGrid extends FrameLayout {

    public interface ProductGridListener {
        void onProductSelected(Product product);
    }

    @InjectView(R.id.grid) GridView grid;

    ProductAdapter adapter;
    ProductGridListener listener;


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

        grid.setFocusable(false);

        grid.setAdapter(adapter = new ProductAdapter(getContext()));
        grid.setOnItemClickListener(onGridItemClick);
    }

    public void clear() {
        adapter.clear();
    }

    public void addProducts(List<Product> products) {
        adapter.addAll(products);
    }

    public void setProducts(List<Product> products) {
        adapter.clear();
        adapter.addAll(products);
    }

    public void setListener(ProductGridListener listener) {
        this.listener = listener;
    }


    private final OnItemClickListener onGridItemClick = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (listener != null)
                listener.onProductSelected(adapter.getItem(position));
        }
    };


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

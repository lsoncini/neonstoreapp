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
import model.Order;
import model.Product;

public class ProductListItem extends FrameLayout{
    @InjectView(R.id.addressName)
    TextView addressName;

    @InjectView(R.id.status)
    TextView status;

    @InjectView(R.id.receivedDate)
    TextView receivedDate;

    @InjectView(R.id.processedDate)
    TextView processedDate;

    @InjectView(R.id.shippedDate)
    TextView shippedDate;

    @InjectView(R.id.deliveredDate)
    TextView deliveredDate;

    @InjectView(R.id.latitude)
    TextView latitude;

    @InjectView(R.id.longitude)
    TextView longitude;

    public ProductListItem(Context context) {
        super(context);
        init();
    }

    public ProductListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProductListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        addView(inflate(getContext(), R.layout.product_detail_order, null));
        ButterKnife.inject(this);
    }

    void setProduct(Order order) {
        addressName.setText(order.address.name);
        status.setText(order.status);

        receivedDate.setText(order.receivedDate.toString());
        processedDate.setText(order.processedDate.toString());
        shippedDate.setText(order.shippedDate.toString());
        deliveredDate.setText(order.deliveredDate.toString());

        latitude.setText(order.latitude.toString());
        latitude.setText(order.longitude.toString());
        price.setText("$" + product.price);
    }
}

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
import model.OrderItem;

public class OrderItemListItem extends FrameLayout {

    @InjectView(R.id.name)     TextView name;
    @InjectView(R.id.price)    TextView price;
    @InjectView(R.id.quantity) TextView quantity;
    @InjectView(R.id.image)    ImageView image;

    public OrderItemListItem(Context context) {
        super(context);
        init();
    }

    public OrderItemListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OrderItemListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        addView(inflate(getContext(), R.layout.order_item_list_item, null));
        ButterKnife.inject(this);
    }

    public void setOrderItem(OrderItem item) {
        name.setText(item.product.name);
        price.setText("$" + item.price);
        quantity.setText(item.quantity);

        Picasso.with(getContext())
            .load(item.product.imageUrl)
            .into(image)
        ;
    }
}

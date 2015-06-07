package activity;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neon.neonstore.R;

import activity.SidebarFragment.SidebarListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Category;
import model.Product;
import view.ProductGrid.ProductGridListener;


public class MainActivity extends ActionBarActivity implements SidebarListener, ProductGridListener {

    @InjectView(R.id.toolbar)       Toolbar toolbar;
    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;


    private SidebarFragment sidebar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sidebar = (SidebarFragment) getSupportFragmentManager()
            .findFragmentById(R.id.fragment_navigation_drawer)
        ;

        sidebar.setUp(R.id.fragment_navigation_drawer, drawerLayout, toolbar);
        sidebar.setListener(this);

        navTo(new HomeFragment());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        RelativeLayout cartLayout = (RelativeLayout) menu.findItem(R.id.action_cart).getActionView();
        TextView cartTV = (TextView) cartLayout.findViewById(R.id.action_counter);
        cartTV.setText(CartFragment.getCounter());
        ImageView cartIV= (ImageView) cartLayout.findViewById(R.id.action_icon);
        cartIV.setImageResource(R.drawable.ic_action_cart);

        RelativeLayout favLayout = (RelativeLayout) menu.findItem(R.id.action_favorites).getActionView();
        TextView favTV = (TextView) favLayout.findViewById(R.id.action_counter);
        favTV.setText(FavoritesFragment.getCounter());
        ImageView favIV= (ImageView) favLayout.findViewById(R.id.action_icon);
        favIV.setImageResource(R.drawable.ic_action_favorites);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Settings action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProductSelected(Product product) {
        navTo(new ProductDetailFragment().setProduct(product));
    }


    @Override
    public void onSidebarHome() {
        navTo(new HomeFragment());
    }

    @Override
    public void onSidebarCategory(Category category) {
        navTo(new CategoryFragment().setCategory(category));
    }

    void navTo(NeonFragment fragment){
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container_body, fragment)
            .commit();

        getSupportActionBar().setTitle(fragment.getTitle());
        drawerLayout.closeDrawers();
    }
}

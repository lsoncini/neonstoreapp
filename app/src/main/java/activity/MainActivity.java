package activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neon.neonstore.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Product;
import view.ProductGrid.ProductGridListener;


public class MainActivity extends ActionBarActivity implements DrawerFragment. FragmentDrawerListener, ProductGridListener {

    @InjectView(R.id.toolbar)       Toolbar toolbar;
    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;

    private DrawerFragment drawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (DrawerFragment) getSupportFragmentManager()
            .findFragmentById(R.id.fragment_navigation_drawer)
        ;

        drawerFragment.setUp(R.id.fragment_navigation_drawer, drawerLayout, toolbar);
        drawerFragment.setDrawerListener(this);

        displayView(0);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Settings action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(id == R.id.action_cart){
            changeFragment(new CartFragment(),getString(R.string.title_cart));
            return true;
        }

        if(id == R.id.action_favorites){
            changeFragment(new FavoritesFragment(),getString(R.string.title_favorites));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new CategoriesFragment();
                title = getString(R.string.title_categories);
                break;
            case 2:
                fragment = new CartFragment();
                title = getString(R.string.title_cart);
                break;
            case 3:
                fragment = new FavoritesFragment();
                title = getString(R.string.title_favorites);
                break;
            case 4:
                fragment = new HelpFragment();
                title = getString(R.string.title_help);
                break;
            default:
                break;
        }

        if (fragment != null) {
            changeFragment(fragment, title);
        }
    }

    void changeFragment(Fragment fragment, String title){
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container_body, fragment)
        .commit();

        // set the toolbar title
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onProductSelected(Product product) {
        changeFragment(
            new ProductDetailFragment().setProduct(product),
            "Producto"
        );
    }
}

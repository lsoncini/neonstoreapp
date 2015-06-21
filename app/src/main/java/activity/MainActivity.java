package activity;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.neon.neonstore.R;

import activity.SidebarFragment.SidebarListener;
import activity.NeonFragment.OnFragmentAttachedListener;
import api.APIQuery;
import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Category;
import model.Product;
import model.Section;
import view.ProductGrid.ProductGridListener;


public class MainActivity extends ActionBarActivity implements SidebarListener, ProductGridListener, OnFragmentAttachedListener {

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
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchBox = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchBox.setOnQueryTextListener(searchListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Settings action is selected!", Toast.LENGTH_SHORT).show();
            return true;

        } else
        if (id == R.id.action_login) {
            navTo(new LoginFragment());
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
    public void onSidebarCategory(Section section, Category category) {
        APIQuery query = new APIQuery()
            .whereCategory(category)
            .whereAge(section.age)
            .whereGender(section.gender)
            .page(1, 8)
            .orderBy(APIQuery.BY_NAME, APIQuery.ASC)
        ;

        navTo(new ProductGridFragment().setQuery(query));


    }

    private final OnQueryTextListener searchListener = new OnQueryTextListener() {
        public boolean onQueryTextSubmit(String queryText) {
            APIQuery query = new APIQuery()
                .whereName(queryText)
                .page(1, 8)
            ;

            navTo(new ProductGridFragment().setQuery(query));
            return false;
        }

        public boolean onQueryTextChange(String queryText) {
            return false;
        }
    };

    void navTo(NeonFragment fragment) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container_body, fragment)
            .addToBackStack("")
            .commit()
        ;

        drawerLayout.closeDrawers();
    }

    public void onFragmentAttached(NeonFragment sender){
        getSupportActionBar().setTitle(sender.getTitle());
    }
}

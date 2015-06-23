package activity;

import android.content.Intent;
import android.content.res.Configuration;
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

import activity.NeonFragment.OnFragmentAttachedListener;
import activity.SidebarFragment.SidebarListener;
import api.APIQuery;
import butterknife.ButterKnife;
import butterknife.InjectView;
import model.Category;
import model.Order;
import model.Product;
import model.Section;
import model.Session;
import notifications.NeonNotificationService;
import notifications.OrderStatusNotification;
import store.SessionListener;
import store.Store;
import view.ProductGrid.ProductGridListener;

public class MainActivity extends ActionBarActivity implements SidebarListener, ProductGridListener, SessionListener, OnFragmentAttachedListener {

    private Store store = Store.getInstance();

    @InjectView(R.id.toolbar)       Toolbar toolbar;
    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    private SearchView searchView;
    private SidebarFragment sidebar;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

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

        store.setSessionListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        navTo(new HomeFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // We may be resuming after a notification was clicked:
        int orderId = getIntent().getIntExtra(OrderStatusNotification.ORDER_ID, -1);

        System.out.println("Intent with order ID " + orderId);

        if (orderId != -1)
            navTo(new OrderDetailFragment().setOrder(new Order(orderId)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(searchListener);
        resetSearchHint();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Settings action is selected!", Toast.LENGTH_SHORT).show();
            return true;

        } else
        /*if (id == R.id.action_login) {
            navTo(new LoginFragment());
            return true;
        }*/

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
    public void onSidebarOrders() {
        navTo(new OrderDetailFragment()); }

    @Override
    public void onSidebarLogIn() { navTo(new LoginFragment());}

    @Override
    public void onSidebarCategory(Section section, Category category) {
        APIQuery query = new APIQuery()
            .whereCategory(category)
            .whereAge(section.age)
            .whereGender(section.gender)
            .page(1, 50)
            .orderBy(APIQuery.BY_NAME, APIQuery.ASC);

        navTo(new ProductGridFragment().setQuery(query));
        setSearchHint(getString(R.string.search_in_hint) + " " + category.name);
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
        resetSearchHint();
    }

    @Override
    public void onLogin(Session session) {
        Intent notificationService = new Intent(this, NeonNotificationService.class);

        notificationService.putExtra("username", session.account.username);
        notificationService.putExtra("authenticationToken", session.authenticationToken);

        startService(notificationService);
    }

    @Override
    public void onLogout() {
        stopService(new Intent(this, NeonNotificationService.class));
    }

    public void onFragmentAttached(NeonFragment sender) {

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setTitle(sender.getTitle());
        }

    }

    private void resetSearchHint() {
        setSearchHint(R.string.search_hint);
    }

    private void setSearchHint(int res) {
        System.out.println(res + ":" + getString(res));
        setSearchHint(getString(res));
    }

    private void setSearchHint(String hint) {
        if (searchView != null)
            searchView.setQueryHint(hint);
    }
}

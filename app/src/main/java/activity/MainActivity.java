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

import com.neon.neonstore.R;

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
import view.OrderList.OrderListListener;
import view.ProductGrid.ProductGridListener;

public class MainActivity extends ActionBarActivity implements SidebarListener, ProductGridListener, SessionListener, OrderListListener {

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

        sidebar = new SidebarFragment();
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.sidebarFrame, sidebar)
        .commit();
//
        store.setSessionListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.fragment_home);
    }

    @Override
    protected void onStart() {
        super.onStart();

        sidebar.setUp(drawerLayout, toolbar);
        sidebar.setListener(this);

        navTo(new HomeFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // We may be resuming after a notification was clicked:
        int orderId = getIntent().getIntExtra(OrderStatusNotification.ORDER_ID, -1);

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
    public void onProductSelected(Product product) {
        navTo(new ProductDetailFragment().setProduct(product));
    }

    @Override
    public void onOrderSelected(Order order) {
        navTo(new OrderDetailFragment().setOrder(order));
    }

    @Override
    public void onSidebarHome() {
        navTo(new HomeFragment());
    }

    @Override
    public void onSidebarOrders() {
        navTo(new OrderListFragment());
    }

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

    public void onBackPressed() {
        System.out.println("BACK PRESSED WITH " + getSupportFragmentManager().getBackStackEntryCount());
        if (sidebar.isOpen())
            sidebar.close();
        else
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            super.onBackPressed();
    }
}

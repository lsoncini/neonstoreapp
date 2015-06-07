package activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.neon.neonstore.R;

import adapter.NavigationDrawerAdapter;
import api.Store;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import model.Category;

public class SidebarFragment extends Fragment {

    private static String TAG = SidebarFragment.class.getSimpleName();
    private static final Store store = Store.getInstance();


    public interface SidebarListener {
        void onSidebarHome();
        void onSidebarCategory(Category category);
    }


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;


    @InjectView(R.id.navHome)       Button   navHome;
    @InjectView(R.id.navCategories) ListView navCategories;

    private SidebarListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidebar, container, false);
        ButterKnife.inject(this, view);

        navCategories.setAdapter(
            new ArrayAdapter<Category>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                store.getCategories()
            )
        );

        return view;
    }


    public void setListener(SidebarListener listener) {
        this.listener = listener;
    }


    @OnClick(R.id.navHome)
    public void onNavHome() {
        if (listener != null) listener.onSidebarHome();
    }

    @OnItemClick(R.id.navCategories)
    public void onNavCategoriesItemClick(int position) {
        if (listener != null)
            listener.onSidebarCategory(store.getCategories().get(position));
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }


//    public static class CategoryAdapter extends ArrayAdapter<Category> {
//
//        public CategoryAdapter(Context context) {
//            super(context, android.R.layout.simple_list_item_1);
//        }
//
//        @Override
//        public View getView(int position, View view, ViewGroup parent) {
//            if (view == null)
//                view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1);
//
//            Category category = getItem(position);
//        }
//    }
}
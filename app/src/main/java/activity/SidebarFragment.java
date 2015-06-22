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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.neon.neonstore.R;

import adapter.NavigationDrawerAdapter;
import store.Store;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import model.Category;
import model.Section;

public class SidebarFragment extends Fragment {

    private static String TAG = SidebarFragment.class.getSimpleName();
    private static final Store store = Store.getInstance();


    public interface SidebarListener {
        void onSidebarHome();
        void onSidebarOrders();
        void onSidebarLogIn();
        void onSidebarCategory(Section section, Category category);
    }


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private boolean wasOpened = false;


    @InjectView(R.id.navHome) Button  navHome;

    @InjectView(R.id.nav)             View     nav;
    @InjectView(R.id.navMainMenu)     View     navMainMenu;
    @InjectView(R.id.navSections)     ListView navSections;
    @InjectView(R.id.navSectionMenu)  View     navSectionMenu;
    @InjectView(R.id.navSectionTitle) TextView navSectionTitle;
    @InjectView(R.id.navCategories)   ListView navCategories;
    @InjectView(R.id.userName)        TextView usernameTextView;
    @InjectView(R.id.profileImage)    ImageView profileImageView;
    @InjectView(R.id.navLogInOrders)        Button    navLogInButton;

    private SidebarListener listener;
    private Section selectedSection;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidebar, container, false);
        ButterKnife.inject(this, view);

        navSections.setAdapter(
            new ArrayAdapter<Section>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                Section.values()
            )
        );

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


    public void selectSection(Section section) {
        selectedSection = section;
        navSectionTitle.setText(selectedSection.name());

//        int offset = -navMainMenu.getWidth();
//        navMainMenu.animate().translationXBy(offset);
//        navSectionMenu.animate().translationXBy(offset);

        navMainMenu.setVisibility(View.GONE);
        navSectionMenu.setVisibility(View.VISIBLE);
    }


    public void unselectSection() {
        selectedSection = null;

//        int offset = navSectionMenu.getWidth();
//        navMainMenu.animate().translationXBy(offset);
//        navSectionMenu.animate().translationXBy(offset);
        navMainMenu.setVisibility(View.VISIBLE);
        navSectionMenu.setVisibility(View.GONE);
    }


    @OnClick(R.id.navHome)
    public void onNavHome() {
        if (listener != null) listener.onSidebarHome();
    }

    @OnClick(R.id.navLogInOrders)
    public void onNavLogIn() {
        if (listener != null){
            Store store = Store.getInstance();
            boolean loggedIn = store.isLoggedIn();
            if(loggedIn){
                listener.onSidebarOrders();
            } else{
                listener.onSidebarLogIn();
            }
        }
    }

    @OnItemClick(R.id.navSections)
    public void onNavSectionsItemClick(int position) {
        selectSection(Section.values()[position]);
    }


    @OnItemClick(R.id.navCategories)
    public void onNavCategoriesItemClick(int position) {
        if (listener != null)
            listener.onSidebarCategory(selectedSection, store.getCategories().get(position));

        unselectSection();
    }

    public void updateSessionSection(){
        Store store = Store.getInstance();
        boolean loggedIn = store.isLoggedIn();

        if(loggedIn){
            profileImageView.setVisibility(View.VISIBLE);
            usernameTextView.setVisibility(View.VISIBLE);
            usernameTextView.setText(store.session.account.firstName);
            navLogInButton.setText(getResources().getString(R.string.title_account));
        } else {
            navLogInButton.setText(getResources().getString(R.string.title_log_in));
            profileImageView.setVisibility(View.GONE);
            usernameTextView.setVisibility(View.GONE);
        }

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

                if(slideOffset==0){
                    wasOpened = false;
                } else {
                    if (!wasOpened){
                        wasOpened = true;
                        updateSessionSection();
                    }
                }
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
//            Category whereCategory = getItem(position);
//        }
//    }
}
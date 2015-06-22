package store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import api.API;
import api.APIBack;
import api.APIQuery;
import api.deserialize.CalendarDeserializer;
import api.deserialize.ProductDeserializer;
import api.response.APIError;
import api.response.LoginResponse;
import api.response.OrderListResponse;
import api.response.OrderResponse;
import api.response.ProductListResponse;
import api.response.ProductResponse;
import api.response.SubcategoryListResponse;
import model.Category;
import model.Product;
import model.Session;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.converter.GsonConverter;

public class Store {

    private static final Store instance = new Store();

    public static Store getInstance() {
        return instance;
    }

    public static API api;

    private SessionListener listener;

    {
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm")
            .registerTypeAdapter(Calendar.class, new CalendarDeserializer())
            .registerTypeAdapter(Product.class, new ProductDeserializer())
        .create();

        api = new RestAdapter.Builder()
            .setEndpoint(API.root)
            .setConverter(new GsonConverter(gson))
            .setLogLevel(LogLevel.FULL)
            .build()
        .create(API.class);
    }

    public Session session;

    private static final Category[] categories = {
        new Category(1, "Calzado"),
        new Category(2, "Indumentaria"),
        new Category(3, "Accesorios"),
    };

    public List<Category> getCategories() {
        return Arrays.asList(categories);
    }


    public void searchProducts(APIQuery query, APIBack<ProductListResponse> apiBack) {
        if (query.name != null)
            searchProductsByName(query, apiBack);
        else
        if (query.subcategory != null)
            searchProductsBySubcategory(query,apiBack);
        else
        if (query.category != null)
            searchProductsByCategory(query, apiBack);
        else
            throw new RuntimeException("Can't search without category or name");
    }

    private void searchProductsByName(APIQuery query, APIBack<ProductListResponse> apiBack) {
        api.getProductsByName(
            query.name,
            query.page,
            query.pageSize,
            query.sortKey,
            query.sortOrder,
            new Gson().toJson(query.filters),
            apiBack
        );
    }

    private void searchProductsByCategory(APIQuery query, APIBack<ProductListResponse> apiBack) {
        api.getProductsByCategory(
                query.category.id,
                query.page,
                query.pageSize,
                query.sortKey,
                query.sortOrder,
                new Gson().toJson(query.filters),
                apiBack
        );
    }

    private void searchProductsBySubcategory(APIQuery query, APIBack<ProductListResponse> apiBack){
        api.getProductsBySubcategory(
                query.subcategory.id,
                query.page,
                query.pageSize,
                query.sortKey,
                query.sortOrder,
                new Gson().toJson(query.filters),
                apiBack
        );
    }

    public void fetchSubcategories(APIQuery query, APIBack<SubcategoryListResponse> apiBack){
        api.getSubCategoriesByCategory(
                query.category.id,
                new Gson().toJson(query.filters),
                apiBack
        );
    }

    public void fetchProduct(int productId, APIBack<ProductResponse> apiBack) {
        api.getProductById(productId, apiBack);
    }

    public void fetchOrders(APIBack<OrderListResponse> apiBack) {
        if (! isLoggedIn())
            throw new RuntimeException("Can't fetchOrders: not logged in");

        api.getAllOrders(session.account.username, session.authenticationToken, apiBack);
    }

    public void fetchOrder(int orderId, APIBack<OrderResponse> apiBack) {
        api.getOrderById(orderId, apiBack);
    }

    public void login(String username, String password) {
        api.login(username, password, new APIBack<LoginResponse>() {
            public void onSuccess(LoginResponse res) {
                session = res.toSession();
                if (listener != null) listener.onLogin(session);
                System.out.println("LOGIN OK");
            }

            public void onError(APIError err) {
                System.out.println("LOGIN ERR " + err);
            }
        });
    }

    public void logout() {
        session = null;
        listener.onLogout();
    }

    public boolean isLoggedIn() {
        return session != null;
    }

    public void setSessionListener(SessionListener listener) {
        this.listener = listener;
    }
}

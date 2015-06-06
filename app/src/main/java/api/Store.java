package api;

import java.util.Arrays;
import java.util.List;

import api.response.ProductListResponse;
import model.Category;
import retrofit.RestAdapter;

public class Store {

    private static final Store instance = new Store();

    public static Store getInstance() {
        return instance;
    }


    private static final API api = new RestAdapter.Builder()
        .setEndpoint(API.root)
        .build()
        .create(API.class)
    ;


    private static final Category[] categories = {
        new Category(1, "Calzado"),
        new Category(2, "Indumentaria"),
        new Category(3, "Accesorios"),
    };


    public List<Category> getCategories() {
        return Arrays.asList(categories);
    }

    public void findProducts(APIQuery query, APIBack<ProductListResponse> apiBack) {
        api.getProductsByCategory(
            query.category.id,
            query.page,
            query.pageSize,
            query.sortKey,
            query.sortOrder,
            apiBack
        );
    }
}

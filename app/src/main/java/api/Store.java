package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import api.deserialize.ProductDeserializer;
import api.response.ProductListResponse;
import api.response.ProductResponse;
import model.Category;
import model.Product;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.converter.GsonConverter;

public class Store {

    private static final Store instance = new Store();

    public static Store getInstance() {
        return instance;
    }


    private static API api;

    {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Product.class, new ProductDeserializer())
        .create();

        api = new RestAdapter.Builder()
            .setEndpoint(API.root)
            .setConverter(new GsonConverter(gson))
            .setLogLevel(LogLevel.FULL)
            .build()
        .create(API.class);
    }


    private static final Category[] categories = {
        new Category(1, "Calzado"),
        new Category(2, "Indumentaria"),
        new Category(3, "Accesorios"),
    };


    public List<Category> getCategories() {
        return Arrays.asList(categories);
    }

    public void searchProducts(APIQuery query, APIBack<ProductListResponse> apiBack) {
        System.out.println(new Gson().toJson(query.filters));
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

    public void fetchProduct(int productId, APIBack<ProductResponse> apiBack) {
        api.getProductById(productId, apiBack);
    }
}

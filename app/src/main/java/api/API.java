package api;

import api.response.CategoryListResponse;
import api.response.LoginResponse;
import api.response.OrderListResponse;
import api.response.ProductListResponse;
import api.response.ProductResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface API {

    String root = "http://eiffel.itba.edu.ar/hci/service3";

    @GET("/Catalog.groovy?method=GetProductById")
    void getProductById(@Query("id") int productId, Callback<ProductResponse> cb);

    @GET("/Catalog.groovy?method=GetProductsByCategoryId")
    void getProductsByCategory(
        @Query("id")         int categoryId,
        @Query("page")       int page,
        @Query("page_size")  int pageSize,
        @Query("sort_key")   String sortKey,
        @Query("sort_order") String sortOrder,
        @Query("filters")    String filters,

        Callback<ProductListResponse> cb
    );

    @GET("/Catalog.groovy?method=GetProductsByName")
    void getProductsByName(
        @Query("name")       String name,
        @Query("page")       int page,
        @Query("page_size")  int pageSize,
        @Query("sort_key")   String sortKey,
        @Query("sort_order") String sortOrder,
        @Query("filters")    String filters,

        Callback<ProductListResponse> cb
    );

    @GET("/Account.groovy?method=SignIn")
    void login(
        @Query("username") String username,
        @Query("password") String password,

        Callback<LoginResponse> cb
    );

    @GET("/Account.groovy?method=SignIn")
    LoginResponse login(
        @Query("username") String username,
        @Query("password") String password
    );

    @GET("/Order.groovy?method=GetAllOrders")
    void getAllOrders(
        @Query("username")             String username,
        @Query("authentication_token") String authenticationToken,

        Callback<OrderListResponse> cb
    );

    @GET("/Order.groovy?method=GetAllOrders")
    OrderListResponse getAllOrders(
        @Query("username")             String username,
        @Query("authentication_token") String authenticationToken
    );

    @GET("/Catalog.groovy?method=GetAllCategories")
    void getAllCategories(Callback<CategoryListResponse> cb);
}

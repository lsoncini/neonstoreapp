package api;

import api.response.CategoryListResponse;
import api.response.ProductListResponse;
import api.response.ProductResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface API {

    String root = "http://eiffel.itba.edu.ar/hci/service3";


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

    @GET("/Catalog.groovy?method=GetAllCategories")
    void getAllCategories(Callback<CategoryListResponse> cb);

    @GET("/Catalog.groovy?method=GetProductById")
    void getProductById(@Query("id") int productId, Callback<ProductResponse> cb);
}

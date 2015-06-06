package api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface API {

    String root = "http://eiffel.itba.edu.ar/hci/service3";


    @GET("/Catalog.groovy?method=GetProductsByCategoryId")
    void getProductsByCategory(@Query("id") int id, Callback<ProductListResponse> cb);

}

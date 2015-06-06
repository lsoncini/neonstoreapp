package api;

import retrofit.http.GET;

public interface API {

    String root = "http://eiffel.itba.edu.ar/hci/service3";


    @GET("/Catalog.groovy?method=GetProductsByCategoryId&id={id}")
    ProductListResponse getProductsByCategory(int id);

}

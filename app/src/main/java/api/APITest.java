package api;

import api.response.APIError;
import api.response.ProductListResponse;
import model.Product;

public class APITest {

    public static void main(String[] args) {
        final Store store = Store.getInstance();

        APIQuery query = new APIQuery()
            .category(store.getCategories().get(0))
            .page(1, 5)
            .orderBy(APIQuery.BY_NAME, APIQuery.ASC)
        ;

        APIBack<ProductListResponse> apiBack =  new APIBack<ProductListResponse>() {
            public void onSuccess(ProductListResponse res) {
                for (Product p: res.products)
                    System.out.println(p.name + ": " + p.price);
            }

            public void onError(APIError err) {
                System.out.println("here");
                System.err.println(err);
            }
        };

        store.findProducts(query, apiBack);
    }

}

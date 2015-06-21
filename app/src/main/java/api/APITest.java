package api;

import api.response.APIError;
import api.response.ProductListResponse;
import api.response.ProductResponse;
import model.Product;
import store.Store;

public class APITest {

    static final Store store = Store.getInstance();


    public static void fetchProductExample() {
        APIBack<ProductResponse> apiBack =  new APIBack<ProductResponse>() {
            public void onSuccess(ProductResponse res) {
                System.out.println(res.product.inspect());
            }

            public void onError(APIError err) {
                System.err.println(err);
            }
        };

        store.fetchProduct(1, apiBack);
    }

    public static void searchProductsExample() {
        APIQuery query = new APIQuery()
            .whereCategory(store.getCategories().get(0))
            .page(1, 5)
            .orderBy(APIQuery.BY_NAME, APIQuery.ASC)
        ;

        APIBack<ProductListResponse> apiBack =  new APIBack<ProductListResponse>() {
            public void onSuccess(ProductListResponse res) {
                for (Product p: res.products)
                    System.out.println(p.inspect());
            }

            public void onError(APIError err) {
                System.err.println(err);
            }
        };

        store.searchProducts(query, apiBack);
    }

    public static void main(String[] args) {
//        searchProductsExample();
        fetchProductExample();
    }

}

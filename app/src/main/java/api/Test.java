package api;

import application.Neon;
import model.Product;

public class Test {


    public static void main(String[] args) {
        API api = Neon.getAPI();

        ProductListResponse res = api.getProductsByCategory(1);

        System.out.println(res.meta.uuid);

        for (Product product : res.products)
            for (String image: product.images)
                System.out.println(image);
    }

}

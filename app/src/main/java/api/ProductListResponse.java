package api;

import java.util.List;

import model.Product;

public class ProductListResponse extends APIResponse {
    int page;
    int pageSize;
    int total;

    List<Product> products;
}

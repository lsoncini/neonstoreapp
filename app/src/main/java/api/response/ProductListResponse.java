package api.response;

import java.util.List;

import model.Product;

public class ProductListResponse extends APIResponse {
    public int page;
    public int pageSize;
    public int total;

    public List<Product> products;
}

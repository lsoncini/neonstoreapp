package api;

import model.Category;

public class APIQuery {

    public static final String
        BY_NAME  = "nombre",
        BY_PRICE = "precio",
        BY_BRAND = "marca"
    ;

    public static final String
        ASC  = "asc",
        DESC = "desc"
    ;


    public Category category;

    public int page;
    public int pageSize;
    public String sortKey;
    public String sortOrder;


    public APIQuery category(Category category) {
        this.category = category;
        return this;
    }

    public APIQuery page(int page) {
        return page(page, 8);
    }

    public APIQuery page(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        return this;
    }

    public APIQuery orderBy(String sortKey) {
        return orderBy(sortKey, ASC);
    }

    public APIQuery orderBy(String sortKey, String sortOrder) {
        this.sortKey = sortKey;
        this.sortOrder = sortOrder;
        return this;
    }
}

package api;

import java.util.LinkedList;
import java.util.List;

import model.Category;
import model.Product;
import model.Product.Age;
import model.Product.Color;
import model.Product.Gender;

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
    public String name;
    public List<APIFilter> filters = new LinkedList<>();

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

    public APIQuery whereColor(Color color) {
        return where(Product.COLOR, color.name());
    }

    public APIQuery whereAge(Age age) {
        return where(Product.AGE, age.name());
    }

    public APIQuery whereGender(Gender gender) {
        return where(Product.GENDER, gender.name());
    }

    public APIQuery whereNew() {
        return where(Product.IS_NEW, "Nuevo");
    }

    public APIQuery whereOffer() {
        return where(Product.IS_OFFER, "Oferta");
    }

    public APIQuery where(int filterId, String value) {
        filters.add(new APIFilter(filterId, value));
        return this;
    }
}

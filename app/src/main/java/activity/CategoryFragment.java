package activity;

import api.APIQuery;
import model.Category;

public class CategoryFragment extends ProductGridFragment {

    public Category category;

    @Override
    public String getTitle() {
        return category.name;
    }

    public CategoryFragment setCategory(Category category) {
        this.category = category;

        setQuery(new APIQuery()
            .whereCategory(category)
            .page(1, 8)
            .orderBy(APIQuery.BY_NAME, APIQuery.ASC)
        );

        return this;
    }

}

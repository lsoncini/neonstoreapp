package activity;

import api.APIQuery;
import model.Category;

public class CategoryFragment extends ProductGridFragment {

    public CategoryFragment setCategory(Category category) {
        setQuery(new APIQuery()
            .category(category)
            .page(1, 8)
            .orderBy(APIQuery.BY_NAME, APIQuery.ASC)
        );

        return this;
    }

}

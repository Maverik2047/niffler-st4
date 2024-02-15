package guru.qa.niffler.api.category;

import guru.qa.niffler.api.RestClient;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.List;

public class CategoryApiClient extends RestClient {

    private final CategoryApi categoryApi;

    public CategoryApiClient() {
        super(Config.getInstance().spendUrl());
        categoryApi = retrofit.create(CategoryApi.class);
    }

    @DisplayName("Add a category")
    public CategoryJson addCategory(CategoryJson category) throws IOException {
        return categoryApi.addCategory(category).execute().body();
    }

    @DisplayName("Get categories for {user}")
    public List<CategoryJson> getCategories(String user) throws IOException {
        return categoryApi.getCategories(user).execute().body();
    }
}

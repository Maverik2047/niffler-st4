package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.category.CategoryApiClient;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Optional;

public class CategoryExtension implements BeforeEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(CategoryExtension.class);

    private final CategoryApiClient categoryApiClient = new CategoryApiClient();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Optional<GenerateCategory> category = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                GenerateCategory.class);

        if (category.isPresent()) {
            GenerateCategory generateCategory = category.get();
            CategoryJson categoryJson = new CategoryJson(
                    null,
                    generateCategory.username(),
                    generateCategory.category()
            );

            categoryApiClient.addCategory(categoryJson);
            extensionContext.getStore(NAMESPACE)
                    .put("category", categoryJson);
        }
    }
}

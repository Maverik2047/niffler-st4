package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Date;
import java.util.Optional;

public abstract class SpendExtension implements BeforeEachCallback {

    abstract SpendJson create(SpendJson spend);

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(SpendExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {

        Optional<GenerateSpend> spend = AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                GenerateSpend.class
        );

        if (spend.isPresent()) {
            GenerateSpend spendData = spend.get();

            CategoryJson categoryJson = (CategoryJson)
                    extensionContext.getStore(CategoryExtension.NAMESPACE).get("category");

            if (categoryJson == null) {
                throw new Exception("Store не содержит данные о категории!");
            }

            SpendJson spendJson = new SpendJson(
                    null,
                    new Date(),
                    categoryJson.category(),
                    spendData.currency(),
                    spendData.amount(),
                    spendData.description(),
                    spendData.username()
            );

            extensionContext.getStore(NAMESPACE)
                    .put(extensionContext.getUniqueId(), create(spendJson));
        }
    }
}
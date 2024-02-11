package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.spend.SpendApiClient;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class RestSpendExtension extends SpendExtension implements BeforeEachCallback {

    @Override
    SpendJson create(SpendJson spend) {
        try {
            return spendApiClient.addSpend(spend);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(RestSpendExtension.class);

    SpendApiClient spendApiClient = new SpendApiClient();

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
                    .put("spend", create(spendJson));
        }
    }
}

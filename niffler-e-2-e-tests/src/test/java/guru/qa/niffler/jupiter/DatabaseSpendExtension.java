package guru.qa.niffler.jupiter;

import guru.qa.niffler.db.model.CategoryEntity;
import guru.qa.niffler.db.model.SpendEntity;
import guru.qa.niffler.db.repository.SpendRepositoryHibernate;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Date;
import java.util.Optional;

public class DatabaseSpendExtension extends SpendExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(DatabaseSpendExtension.class);

    @Override
    SpendJson create(SpendJson spend) {

        CategoryEntity category = new CategoryEntity();
        category.setCategory(spend.category());
        category.setUsername(spend.username());

        SpendEntity spendEntity = new SpendEntity();
        spendEntity.setSpendDate(spend.spendDate());
        spendEntity.setCurrency(spend.currency());
        spendEntity.setCategory(category);
        spendEntity.setDescription(spend.description());
        spendEntity.setAmount(spend.amount());

        SpendRepositoryHibernate spendRepositoryHibernate = new SpendRepositoryHibernate();
        SpendEntity created = spendRepositoryHibernate.createSpend(spendEntity);

        return new SpendJson(
                created.getId(),
                created.getSpendDate(),
                created.getCategory().getCategory(),
                created.getCurrency(),
                created.getAmount(),
                created.getDescription(),
                created.getUsername()
        );
    }

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

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType().isAssignableFrom(SpendEntity.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), SpendJson.class);
    }
}
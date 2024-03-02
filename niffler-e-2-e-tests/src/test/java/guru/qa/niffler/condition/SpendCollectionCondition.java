package guru.qa.niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.MatcherError;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SpendCollectionCondition {

    public static CollectionCondition spends(SpendJson... expectedSpends) {

        return new CollectionCondition() {

            @Nonnull
            @Override
            public CheckResult check(Driver driver, List<WebElement> elements) {

                if (elements.size() != expectedSpends.length) {

                    return CheckResult.rejected("Incorrect table size: " + elements.size(), elements);
                }
                for (int i = 0; i < expectedSpends.length; i++) {

                    WebElement row = elements.get(i);
                    SpendJson expectedSpending = expectedSpends[i];

                    List<WebElement> cells = row.findElements(By.cssSelector("td"));

                    SpendJson actualSpendInTable = new SpendJson(
                            null,
                            new Date(),
                            cells.get(4).getText(),
                            CurrencyValues.valueOf(cells.get(3).getText()),
                            Double.valueOf(cells.get(2).getText()),
                            cells.get(5).getText(),
                            null
                    );

                    if (!new SimpleDateFormat("dd MMM yy", Locale.ENGLISH)
                            .format(expectedSpending.spendDate()).equals(cells.get(1).getText())) {

                        return CheckResult.rejected("Incorrect spends content", List.of(expectedSpending,
                                actualSpendInTable));
                    }
                    if (!expectedSpending.amount().equals(Double.valueOf(cells.get(2).getText()))) {

                        return CheckResult.rejected("Incorrect spends content", List.of(expectedSpending,
                                actualSpendInTable));
                    }
                    if (!expectedSpending.currency().name().equals(cells.get(3).getText())) {

                        return CheckResult.rejected("Incorrect spends content", List.of(expectedSpending,
                                actualSpendInTable));
                    }
                    if (!expectedSpending.category().equals(cells.get(4).getText())) {

                        return CheckResult.rejected("Incorrect spends content", List.of(expectedSpending,
                                actualSpendInTable));
                    }
                    if (!expectedSpending.description().equals(cells.get(5).getText())) {

                        return CheckResult.rejected("Incorrect spends content", List.of(expectedSpending,
                                actualSpendInTable));
                    }
                }
                return CheckResult.accepted();
            }

            @Override
            public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
                List<String> actualTexts = lastCheckResult.getActualValue();

                if (actualTexts == null || actualTexts.isEmpty()) {
                    throw new ElementNotFound(collection, toString(), timeoutMs, cause);
                }

                throw new MatcherError(explanation, ((List) lastCheckResult.getActualValue()).get(0).toString(),
                        ((List) lastCheckResult.getActualValue()).get(1).toString(), collection, cause, timeoutMs);
            }

            @Override
            public boolean missingElementSatisfiesCondition() {
                return false;
            }
        };
    }
}

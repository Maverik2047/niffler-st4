package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.spend.SpendApiClient;
import guru.qa.niffler.model.SpendJson;

import java.io.IOException;

public class RestSpendExtension extends SpendExtension {

    SpendApiClient spendApiClient = new SpendApiClient();

    @Override
    SpendJson create(SpendJson spend) {
        try {
            return spendApiClient.addSpend(spend);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

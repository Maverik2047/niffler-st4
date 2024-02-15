package guru.qa.niffler.api.spend;

import guru.qa.niffler.api.RestClient;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.DisplayName;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SpendApiClient extends RestClient {

    private final SpendApi spendApi;

    public SpendApiClient() {
        super(Config.getInstance().spendUrl());
        spendApi = retrofit.create(SpendApi.class);
    }

    @DisplayName("Add a spend")
    public SpendJson addSpend(SpendJson spendJson) throws IOException {
        return spendApi.addSpend(spendJson).execute().body();
    }

    @DisplayName("Get spends for {userName}")
    public List<SpendJson> getSpends(String userName, @Nullable CurrencyValues filterCurrency,
                                     @Nullable Date from, @Nullable Date to) throws IOException {
        return spendApi.getSpends(userName, filterCurrency, from, to).execute().body();
    }
}

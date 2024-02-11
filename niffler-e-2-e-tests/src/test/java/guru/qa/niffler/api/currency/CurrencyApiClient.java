package guru.qa.niffler.api.currency;

import guru.qa.niffler.api.RestClient;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.CurrencyCalculateJson;
import guru.qa.niffler.model.CurrencyJson;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.List;

public class CurrencyApiClient extends RestClient {

    private final CurrencyApi currencyApi;

    public CurrencyApiClient() {
        super(Config.getInstance().currencyUrl());
        currencyApi = retrofit.create(CurrencyApi.class);
    }

    @DisplayName("Get all currencies")
    public List<CurrencyJson> getAllCurrencies() throws IOException {
        return currencyApi.getAllCurrencies().execute().body();
    }

    @DisplayName("Get all currencies calculated")
    public CurrencyCalculateJson getAllCurrenciesCalculated(CurrencyCalculateJson currencyCalculate) throws IOException {
        return currencyApi.getAllCurrencies(currencyCalculate).execute().body();
    }
}

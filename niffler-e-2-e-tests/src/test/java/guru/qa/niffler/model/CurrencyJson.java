package guru.qa.niffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.niffler.db.model.CurrencyEntity;
import guru.qa.niffler.db.model.CurrencyValues;

import javax.annotation.Nonnull;

public record CurrencyJson(
        @JsonProperty("currency")
        CurrencyValues currency,
        @JsonProperty("currencyRate")
        Double currencyRate) {

    public static @Nonnull
    CurrencyJson fromEntity(@Nonnull CurrencyEntity entity) {
        return new CurrencyJson(entity.getCurrency(), entity.getCurrencyRate());
    }
}
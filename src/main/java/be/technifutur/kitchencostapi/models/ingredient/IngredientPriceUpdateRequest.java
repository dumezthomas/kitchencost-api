package be.technifutur.kitchencostapi.models.ingredient;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IngredientPriceUpdateRequest(

        @NotNull
        @DecimalMin(value = "0.0")
        BigDecimal unitPrice
) {

}

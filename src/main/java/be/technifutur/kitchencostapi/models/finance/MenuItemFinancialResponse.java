package be.technifutur.kitchencostapi.models.finance;

import java.math.BigDecimal;

public record MenuItemFinancialResponse(

        String name,
        BigDecimal sellingPrice,

        BigDecimal recipeCost,

        BigDecimal margin,
        BigDecimal foodCostPercentage,

        boolean profitable
) {
}

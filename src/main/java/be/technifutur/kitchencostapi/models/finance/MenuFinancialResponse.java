package be.technifutur.kitchencostapi.models.finance;

import java.math.BigDecimal;
import java.util.List;

public record MenuFinancialResponse(
        
        BigDecimal averageMargin,
        BigDecimal globalFoodCostPercentage,

        long profitableItems,
        long nonProfitableItems,

        List<MenuItemFinancialResponse> items
) {

}

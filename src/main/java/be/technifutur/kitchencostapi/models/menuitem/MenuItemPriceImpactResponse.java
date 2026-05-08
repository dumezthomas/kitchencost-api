package be.technifutur.kitchencostapi.models.menuitem;

import java.math.BigDecimal;

public record MenuItemPriceImpactResponse(

        String menuItemName,
        BigDecimal sellingPrice,

        BigDecimal oldRecipeCost,
        BigDecimal newRecipeCost,

        BigDecimal oldMargin,
        BigDecimal newMargin,

        BigDecimal oldFoodCostPercentage,
        BigDecimal newFoodCostPercentage,

        boolean profitableBefore,
        boolean profitableAfter
) {

}

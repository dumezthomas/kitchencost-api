package be.technifutur.kitchencostapi.models.ingredient;

import be.technifutur.kitchencostapi.enums.Unit;
import be.technifutur.kitchencostapi.models.menuitem.MenuItemPriceImpactResponse;

import java.math.BigDecimal;
import java.util.List;

public record IngredientPriceImpactResponse(
        String ingredientName,
        Unit unit,
        BigDecimal oldUnitPrice,
        BigDecimal newUnitPrice,

        BigDecimal priceChangePercentage,

        List<MenuItemPriceImpactResponse> affectedMenuItems
) {

}

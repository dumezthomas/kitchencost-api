package be.technifutur.kitchencostapi.models.recipeingredient;

import be.technifutur.kitchencostapi.enums.Allergen;
import be.technifutur.kitchencostapi.enums.IngredientOrigin;
import be.technifutur.kitchencostapi.enums.Unit;

import java.math.BigDecimal;
import java.util.Set;

public record RecipeIngredientResponse(

        String name,
        BigDecimal quantity,
        Unit unit,
        Set<Allergen> allergens,
        IngredientOrigin origin
) {
}

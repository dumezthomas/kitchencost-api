package be.technifutur.kitchencostapi.models.recipe;

import be.technifutur.kitchencostapi.enums.MenuItemType;
import be.technifutur.kitchencostapi.models.recipeingredient.RecipeIngredientResponse;

import java.util.Set;

public record RecipeResponse(

        String name,
        MenuItemType type,

        String instructions,
        Set<RecipeIngredientResponse> ingredients
) {
}

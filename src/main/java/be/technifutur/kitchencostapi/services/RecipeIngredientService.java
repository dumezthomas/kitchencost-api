package be.technifutur.kitchencostapi.services;

import be.technifutur.kitchencostapi.models.recipeingredient.RecipeIngredientResponse;
import be.technifutur.kitchencostapi.pojos.RecipeIngredient;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RecipeIngredientService {

    public RecipeIngredientResponse toResponse(RecipeIngredient ri) {

        return new RecipeIngredientResponse(
                ri.getIngredient().getName(),
                ri.getQuantity(),
                ri.getIngredient().getUnit(),
                ri.getIngredient().getAllergens(),
                ri.getIngredient().getOrigin()
        );
    }
}

package be.technifutur.kitchencostapi.services;

import be.technifutur.kitchencostapi.enums.Allergen;
import be.technifutur.kitchencostapi.enums.IngredientOrigin;
import be.technifutur.kitchencostapi.models.recipeingredient.RecipeIngredientResponse;
import be.technifutur.kitchencostapi.pojos.Recipe;
import be.technifutur.kitchencostapi.pojos.RecipeIngredient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class RecipeService {

    @Inject
    RecipeIngredientService recipeIngredientService;

    public Set<String> getIngredients(Recipe r) {

        return r.getIngredients()
                .stream()
                .map(ri -> ri.getIngredient().getName())
                .collect(Collectors.toSet());
    }

    public Set<Allergen> getAllergens(Recipe r) {

        return r.getIngredients()
                .stream()
                .map(RecipeIngredient::getIngredient)
                .flatMap(i -> i.getAllergens().stream())
                .collect(Collectors.toSet());
    }

    public boolean isVegan(Recipe r) {

        return r.getIngredients()
                .stream()
                .map(ri -> ri.getIngredient().getOrigin())
                .allMatch(io -> io == IngredientOrigin.PLANT);
    }

    public boolean isVegetarian(Recipe r) {

        return r.getIngredients()
                .stream()
                .map(ri -> ri.getIngredient().getOrigin())
                .noneMatch(io -> io == IngredientOrigin.MEAT);
    }

    public Set<RecipeIngredientResponse> getRecipeIngredients(Recipe r) {

        return r.getIngredients()
                .stream()
                .map(recipeIngredientService::toResponse)
                .collect(Collectors.toSet());
    }
}

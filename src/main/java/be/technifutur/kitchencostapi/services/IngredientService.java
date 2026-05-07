package be.technifutur.kitchencostapi.services;

import be.technifutur.kitchencostapi.models.ingredient.IngredientPriceImpactResponse;
import be.technifutur.kitchencostapi.models.ingredient.IngredientPriceUpdateRequest;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class IngredientService {

    public Optional<IngredientPriceImpactResponse> updateIngredientPrice(Long id, IngredientPriceUpdateRequest request) {

        return Optional.empty();
    }
}

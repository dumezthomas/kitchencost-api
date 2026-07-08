package be.technifutur.kitchencostapi.models.menuitem;

import be.technifutur.kitchencostapi.enums.Allergen;
import be.technifutur.kitchencostapi.enums.MenuItemType;

import java.math.BigDecimal;
import java.util.Set;

public record MenuItemResponse(

        Long id,

        String name,
        String description,
        BigDecimal price,
        MenuItemType type,

        Set<String> ingredients,
        Set<Allergen> allergens,

        boolean vegan,
        boolean vegetarian
) {

}

package be.technifutur.kitchencostapi.services;

import be.technifutur.kitchencostapi.daos.MenuItemDao;
import be.technifutur.kitchencostapi.enums.MenuItemStatus;
import be.technifutur.kitchencostapi.models.menuitem.MenuItemResponse;
import be.technifutur.kitchencostapi.pojos.MenuItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MenuItemService {

    @Inject
    MenuItemDao menuItemDao;

    @Inject
    RecipeService recipeService;

    public List<MenuItemResponse> getAvailableMenuItems() {

        return menuItemDao.findByStatus(MenuItemStatus.AVAILABLE)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<MenuItemResponse> getAvailableMenuItem(Long id) {

        return menuItemDao.findByIdAndStatus(id, MenuItemStatus.AVAILABLE)
                .map(this::toResponse);
    }

    private MenuItemResponse toResponse(MenuItem mi) {

        return new MenuItemResponse(
                mi.getId(),

                mi.getName(),
                mi.getDescription(),
                mi.getSellingPrice(),
                mi.getType(),

                recipeService.getIngredients(mi.getRecipe()),
                recipeService.getAllergens(mi.getRecipe()),

                recipeService.isVegan(mi.getRecipe()),
                recipeService.isVegetarian(mi.getRecipe())
        );
    }
}

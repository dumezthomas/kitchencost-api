package be.technifutur.kitchencostapi.services;

import be.technifutur.kitchencostapi.daos.MenuItemDao;
import be.technifutur.kitchencostapi.enums.MenuItemStatus;
import be.technifutur.kitchencostapi.models.menuitem.MenuItemClientResponse;
import be.technifutur.kitchencostapi.models.menuitem.MenuItemCookResponse;
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

    public List<MenuItemClientResponse> getAvailableMenuItemsForClient() {

        return menuItemDao.findByStatus(MenuItemStatus.AVAILABLE)
                .stream()
                .map(this::toClientResponse)
                .toList();
    }

    public Optional<MenuItemClientResponse> getAvailableMenuItemForClient(Long id) {

        return menuItemDao.findByIdAndStatus(id, MenuItemStatus.AVAILABLE)
                .map(this::toClientResponse);
    }

    public List<MenuItemCookResponse> getMenuItemsForCook() {

        return menuItemDao.findAll()
                .stream()
                .map(this::toCookResponse)
                .toList();
    }

    public Optional<MenuItemCookResponse> getMenuItemForCook(Long id) {

        return menuItemDao.findById(id)
                .map(this::toCookResponse);
    }

    private MenuItemClientResponse toClientResponse(MenuItem mi) {

        return new MenuItemClientResponse(
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

    private MenuItemCookResponse toCookResponse(MenuItem mi) {

        return new MenuItemCookResponse(
                mi.getName(),
                mi.getType(),

                mi.getRecipe().getInstructions(),
                recipeService.getRecipeIngredients(mi.getRecipe())
        );
    }
}

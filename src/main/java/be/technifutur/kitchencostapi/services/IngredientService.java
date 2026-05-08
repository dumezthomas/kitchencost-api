package be.technifutur.kitchencostapi.services;

import be.technifutur.kitchencostapi.daos.IngredientDao;
import be.technifutur.kitchencostapi.daos.MenuItemDao;
import be.technifutur.kitchencostapi.models.finance.MenuItemFinancialResponse;
import be.technifutur.kitchencostapi.models.ingredient.IngredientPriceImpactResponse;
import be.technifutur.kitchencostapi.models.ingredient.IngredientPriceUpdateRequest;
import be.technifutur.kitchencostapi.models.menuitem.MenuItemPriceImpactResponse;
import be.technifutur.kitchencostapi.pojos.Ingredient;
import be.technifutur.kitchencostapi.pojos.MenuItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static be.technifutur.kitchencostapi.utils.Helpers.round;

@ApplicationScoped
public class IngredientService {

    @Inject
    IngredientDao ingredientDao;

    @Inject
    MenuItemDao menuItemDao;

    @Inject
    FinancialReportService financialReportService;

    @Transactional
    public Optional<IngredientPriceImpactResponse> updateIngredientPrice(Long id, IngredientPriceUpdateRequest request) {

        return ingredientDao.findById(id)
                .map(i -> {

                    BigDecimal oldUnitPrice = i.getUnitPrice();
                    Map<MenuItem, MenuItemFinancialResponse> oldFinancials = financialReportService
                            .getMenuItemsFinancialReport(menuItemDao.findByIngredientId(i.getId()));

                    i.setUnitPrice(request.unitPrice());
                    ingredientDao.update(i);

                    Map<MenuItem, MenuItemFinancialResponse> newFinancials = financialReportService
                            .getMenuItemsFinancialReport(menuItemDao.findByIngredientId(i.getId()));

                    return toResponse(i, oldUnitPrice, oldFinancials, newFinancials);
                });
    }

    private IngredientPriceImpactResponse toResponse(Ingredient i,
                                                     BigDecimal oldUnitPrice,
                                                     Map<MenuItem, MenuItemFinancialResponse> oldFinancials,
                                                     Map<MenuItem, MenuItemFinancialResponse> newFinancials) {

        return new IngredientPriceImpactResponse(
                i.getName(),
                i.getUnit(),
                oldUnitPrice,
                i.getUnitPrice(),

                round(financialReportService.calculatePriceChangePercentage(oldUnitPrice, i.getUnitPrice())),

                toMenuItemPriceImpactResponses(oldFinancials, newFinancials)
        );
    }

    private List<MenuItemPriceImpactResponse> toMenuItemPriceImpactResponses(
            Map<MenuItem, MenuItemFinancialResponse> oldFinancials,
            Map<MenuItem, MenuItemFinancialResponse> newFinancials
    ) {

        return oldFinancials.entrySet()
                .stream()
                .map(entry -> toMenuItemPriceImpactResponse(
                        entry.getKey(),
                        entry.getValue(),
                        newFinancials.get(entry.getKey())
                ))
                .toList();
    }

    private MenuItemPriceImpactResponse toMenuItemPriceImpactResponse(
            MenuItem mi,
            MenuItemFinancialResponse oldFinancial,
            MenuItemFinancialResponse newFinancial
    ) {

        return new MenuItemPriceImpactResponse(
                mi.getName(),
                mi.getSellingPrice(),

                oldFinancial.recipeCost(),
                newFinancial.recipeCost(),

                oldFinancial.margin(),
                newFinancial.margin(),

                oldFinancial.foodCostPercentage(),
                newFinancial.foodCostPercentage(),

                oldFinancial.profitable(),
                newFinancial.profitable()
        );
    }
}
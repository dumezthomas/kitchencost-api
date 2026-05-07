package be.technifutur.kitchencostapi.services;

import be.technifutur.kitchencostapi.daos.MenuItemDao;
import be.technifutur.kitchencostapi.enums.MenuItemStatus;
import be.technifutur.kitchencostapi.models.finance.MenuFinancialResponse;
import be.technifutur.kitchencostapi.models.finance.MenuItemFinancialResponse;
import be.technifutur.kitchencostapi.pojos.MenuItem;
import be.technifutur.kitchencostapi.pojos.Recipe;
import be.technifutur.kitchencostapi.pojos.RecipeIngredient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FinancialReportService {

    @Inject
    MenuItemDao menuItemDao;

    private static final BigDecimal PROFITABLE_THRESHOLD = BigDecimal.valueOf(30);

    public MenuFinancialResponse getMenuFinancialReport(MenuItemStatus status) {

        List<MenuItemFinancialResponse> items =
                (status == null ? menuItemDao.findAll() : menuItemDao.findByStatus(status))
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return toResponse(items);
    }

    public Optional<MenuItemFinancialResponse> getMenuItemFinancialReport(Long id) {

        return menuItemDao.findById(id)
                .map(this::toResponse);
    }

    private MenuFinancialResponse toResponse(List<MenuItemFinancialResponse> items) {

        long nbProfitableItems = nbProfitableItems(items);

        return new MenuFinancialResponse(
                round(calculateAverageMargin(items)),
                round(calculateGlobalFoodCostPercentage(items)),

                nbProfitableItems,
                items.size() - nbProfitableItems,

                items
        );
    }

    private MenuItemFinancialResponse toResponse(MenuItem mi) {

        BigDecimal recipeCost = calculateRecipeCost(mi.getRecipe());
        BigDecimal foodCostPercentage = calculateFoodCostPercentage(mi.getSellingPrice(), recipeCost);

        return new MenuItemFinancialResponse(
                mi.getName(),
                mi.getSellingPrice(),

                round(recipeCost),
                round(calculateMargin(mi.getSellingPrice(), recipeCost)),
                round(foodCostPercentage),

                isProfitable(foodCostPercentage)
        );
    }

    private BigDecimal calculateRecipeCost(Recipe r) {

        return r.getIngredients()
                .stream()
                .map(RecipeIngredient::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateMargin(BigDecimal sellingPrice, BigDecimal recipeCost) {

        return sellingPrice.subtract(recipeCost);
    }

    private BigDecimal calculateAverageMargin(List<MenuItemFinancialResponse> items) {

        if (items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return items
                .stream()
                .map(MenuItemFinancialResponse::margin)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(items.size()), 4, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateFoodCostPercentage(BigDecimal sellingPrice, BigDecimal recipeCost) {

        if (sellingPrice.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        return recipeCost
                .divide(sellingPrice, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal calculateGlobalFoodCostPercentage(List<MenuItemFinancialResponse> items) {

        BigDecimal globalSellingPrice = items.stream()
                .map(MenuItemFinancialResponse::sellingPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal globalRecipeCost = items.stream()
                .map(MenuItemFinancialResponse::recipeCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return calculateFoodCostPercentage(globalSellingPrice, globalRecipeCost);
    }

    private boolean isProfitable(BigDecimal foodCostPercentage) {

        if (foodCostPercentage == null) {
            return false;
        }

        return foodCostPercentage.compareTo(PROFITABLE_THRESHOLD) < 0;
    }

    private long nbProfitableItems(List<MenuItemFinancialResponse> items) {

        return items
                .stream()
                .filter(MenuItemFinancialResponse::profitable)
                .count();
    }

    private BigDecimal round(BigDecimal value) {

        if (value == null) {
            return null;
        }

        return value.setScale(2, RoundingMode.HALF_UP);
    }
}

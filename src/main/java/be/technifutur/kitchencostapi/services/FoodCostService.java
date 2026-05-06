package be.technifutur.kitchencostapi.services;

import be.technifutur.kitchencostapi.daos.MenuItemDao;
import be.technifutur.kitchencostapi.models.finance.MenuItemFinancialResponse;
import be.technifutur.kitchencostapi.pojos.MenuItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FoodCostService {

    @Inject
    MenuItemDao menuItemDao;

    public List<MenuItemFinancialResponse> getMenuItemsFinancial() {

        return menuItemDao.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<MenuItemFinancialResponse> getMenuItemFinancial(Long id) {

        return menuItemDao.findById(id)
                .map(this::toResponse);
    }

    private MenuItemFinancialResponse toResponse(MenuItem mi) {

        return new MenuItemFinancialResponse(
                mi.getName(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                false
        );
    }
}

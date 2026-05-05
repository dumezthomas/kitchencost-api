package be.technifutur.kitchencostapi.daos;

import be.technifutur.kitchencostapi.pojos.MenuItem;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MenuItemDao extends CrudDao<MenuItem, Long> {
}

package be.technifutur.kitchencostapi.daos;

import be.technifutur.kitchencostapi.enums.MenuItemStatus;
import be.technifutur.kitchencostapi.pojos.MenuItem;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MenuItemDao extends CrudDao<MenuItem, Long> {

    private static final String FETCH_QUERY = """
            SELECT mi FROM MenuItem mi
            JOIN FETCH mi.recipe r
            JOIN FETCH r.ingredients ri
            JOIN FETCH ri.ingredient i
            """;

    @Override
    public List<MenuItem> findAll() {

        try (var em = emf.createEntityManager()) {
            return em.createQuery(
                            FETCH_QUERY +
                                    " ORDER BY mi.type",
                            MenuItem.class)
                    .getResultList();
        }
    }

    public List<MenuItem> findByStatus(MenuItemStatus status) {

        try (var em = emf.createEntityManager()) {
            return em.createQuery(
                            FETCH_QUERY +
                                    " WHERE mi.status = :status" +
                                    " ORDER BY mi.type",
                            MenuItem.class)
                    .setParameter("status", status)
                    .getResultList();
        }
    }

    public List<MenuItem> findByIngredientId(Long ingredientId) {

        try (var em = emf.createEntityManager()) {
            return em.createQuery(
                            FETCH_QUERY +
                                    " WHERE i.id = :id" +
                                    " ORDER BY mi.type",
                            MenuItem.class)
                    .setParameter("id", ingredientId)
                    .getResultList();
        }
    }

    public Optional<MenuItem> findById(Long id) {

        try (var em = emf.createEntityManager()) {
            return em.createQuery(
                            FETCH_QUERY +
                                    " WHERE mi.id = :id",
                            MenuItem.class)
                    .setParameter("id", id)
                    .getResultStream().findFirst();
        }
    }

    public Optional<MenuItem> findByIdAndStatus(Long id, MenuItemStatus status) {

        try (var em = emf.createEntityManager()) {
            return em.createQuery(
                            FETCH_QUERY +
                                    " WHERE mi.id = :id" +
                                    " AND mi.status = :status",
                            MenuItem.class)
                    .setParameter("id", id)
                    .setParameter("status", status)
                    .getResultStream().findFirst();
        }
    }
}

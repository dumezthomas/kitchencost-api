package be.technifutur.kitchencostapi.daos;

import be.technifutur.kitchencostapi.pojos.Recipe;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RecipeDao extends CrudDao<Recipe, Long> {

    public Optional<Recipe> findByName(String name) {
        try (var em = emf.createEntityManager()) {
            List<Recipe> recipe = em.createQuery(
                            "SELECT r FROM Recipe r WHERE r.name = :name",
                            Recipe.class)
                    .setParameter("name", name)
                    .getResultList();

            return recipe.stream().findFirst();
        }
    }
}

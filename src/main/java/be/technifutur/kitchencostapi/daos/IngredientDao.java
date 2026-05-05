package be.technifutur.kitchencostapi.daos;

import be.technifutur.kitchencostapi.pojos.Ingredient;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class IngredientDao extends CrudDao<Ingredient, Long> {

    public Optional<Ingredient> findByName(String name) {
        try (var em = emf.createEntityManager()) {
            List<Ingredient> ingredients = em.createQuery(
                            "SELECT i FROM Ingredient i WHERE i.name = :name",
                            Ingredient.class)
                    .setParameter("name", name)
                    .getResultList();

            return ingredients.stream().findFirst();
        }
    }
}

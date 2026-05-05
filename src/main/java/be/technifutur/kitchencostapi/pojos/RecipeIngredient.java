package be.technifutur.kitchencostapi.pojos;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "recipe_ingredients",
       uniqueConstraints = {
               @UniqueConstraint(columnNames = {"recipe_id", "ingredient_id"})
       })
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class RecipeIngredient {

    @Getter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @EqualsAndHashCode.Include
    @ManyToOne(optional = false,
               fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Getter @Setter
    @EqualsAndHashCode.Include
    @ManyToOne(optional = false,
               fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Getter @Setter
    @DecimalMin("0.0")
    @Column(nullable = false,
            precision = 10,
            scale = 4)
    private BigDecimal quantity;

    public RecipeIngredient(Recipe recipe, Ingredient ingredient, BigDecimal quantity) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        if (ingredient == null || ingredient.getUnitPrice() == null || quantity == null) {
            return BigDecimal.ZERO;
        }

        return ingredient.getUnitPrice().multiply(quantity);
    }
}

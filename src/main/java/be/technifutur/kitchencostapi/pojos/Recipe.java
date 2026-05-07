package be.technifutur.kitchencostapi.pojos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipes")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Recipe {

    @Getter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(length = 150,
            nullable = false,
            unique = true)
    private String name;

    @Getter @Setter
    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Getter
    @OneToMany(mappedBy = "recipe",
               orphanRemoval = true,
               fetch = FetchType.LAZY,
               cascade = CascadeType.PERSIST)
    private Set<RecipeIngredient> ingredients = new HashSet<>();

    public Recipe(String name, String instructions) {

        this.name = name;
        this.instructions = instructions;
    }

    public void addIngredient(Ingredient ingredient, BigDecimal quantity) {

        RecipeIngredient recipeIngredient = new RecipeIngredient(this, ingredient, quantity);
        this.ingredients.add(recipeIngredient);
    }

    public void removeIngredient(Ingredient ingredient) {

        this.ingredients.removeIf(ri -> ri.getIngredient().equals(ingredient));
    }
}

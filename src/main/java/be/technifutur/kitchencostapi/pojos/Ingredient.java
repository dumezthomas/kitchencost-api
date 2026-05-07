package be.technifutur.kitchencostapi.pojos;

import be.technifutur.kitchencostapi.enums.Allergen;
import be.technifutur.kitchencostapi.enums.IngredientOrigin;
import be.technifutur.kitchencostapi.enums.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredients")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Ingredient {

    @Getter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(length = 100,
            nullable = false,
            unique = true)
    private String name;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @ToString.Include
    @Column(length = 50,
            nullable = false)
    private Unit unit;

    @Getter @Setter
    @DecimalMin("0.0")
    @ToString.Include
    @Column(name = "unit_price",
            nullable = false,
            precision = 10,
            scale = 4)
    private BigDecimal unitPrice;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IngredientOrigin origin;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Set<Allergen> allergens = new HashSet<>();

    public Ingredient(String name, Unit unit, BigDecimal unitPrice, IngredientOrigin origin) {

        this.name = name;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.origin = origin;
    }

    public void addAllergen(Allergen allergen) {

        this.allergens.add(allergen);
    }

    public void removeAllergen(Allergen allergen) {

        this.allergens.remove(allergen);
    }
}

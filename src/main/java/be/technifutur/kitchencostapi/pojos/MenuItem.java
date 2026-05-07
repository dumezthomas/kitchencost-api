package be.technifutur.kitchencostapi.pojos;

import be.technifutur.kitchencostapi.enums.MenuItemStatus;
import be.technifutur.kitchencostapi.enums.MenuItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class MenuItem {

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
    private String description;

    @Getter @Setter
    @DecimalMin("0.0")
    @Column(name = "selling_price",
            nullable = false,
            precision = 10,
            scale = 2)
    private BigDecimal sellingPrice;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 50,
            nullable = false)
    private MenuItemStatus status;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 50,
            nullable = false)
    private MenuItemType type;

    @Getter @Setter
    @ManyToOne(optional = false,
               fetch = FetchType.LAZY)
    private Recipe recipe;

    public MenuItem(String name, String description,
                    BigDecimal sellingPrice,
                    MenuItemStatus status,
                    MenuItemType type,
                    Recipe recipe) {

        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.status = status;
        this.type = type;
        this.recipe = recipe;
    }
}

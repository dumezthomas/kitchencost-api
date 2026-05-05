package be.technifutur.kitchencostapi.pojos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Role {

    @Getter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(length = 50, nullable = false, unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}

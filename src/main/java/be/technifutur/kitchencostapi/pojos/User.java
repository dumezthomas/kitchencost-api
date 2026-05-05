package be.technifutur.kitchencostapi.pojos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class User {

    @Getter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(length = 50,
            nullable = false,
            unique = true)
    private String username;

    @Getter @Setter
    @Column(length = 150,
            nullable = false,
            unique = true)
    private String email;

    @Getter @Setter
    @Column(nullable = false)
    private String password;

    @Getter @Setter
    @ManyToOne(optional = false,
               fetch = FetchType.LAZY)
    private Role role;

    public User(String email, String username, String password, Role role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

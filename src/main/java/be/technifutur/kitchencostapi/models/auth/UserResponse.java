package be.technifutur.kitchencostapi.models.auth;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.security.Principal;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class UserResponse implements Principal {

    private Integer id;

    @EqualsAndHashCode.Include
    @ToString.Include
    private String username;

    private String email;

    private String role;

    @Override
    public String getName() {

        return this.username;
    }
}


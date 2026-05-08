package be.technifutur.kitchencostapi.config;

import be.technifutur.kitchencostapi.models.auth.UserResponse;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;

public class SecurityContextImpl implements SecurityContext {

    private final UserResponse user;

    public SecurityContextImpl(UserResponse user) {

        this.user = user;
    }


    @Override
    public Principal getUserPrincipal() {

        return user;
    }

    @Override
    public boolean isUserInRole(String role) {

        return this.user.getRole().equals(role);
    }

    @Override
    public boolean isSecure() {

        return true;
    }

    @Override
    public String getAuthenticationScheme() {

        return "Bearer";
    }
}

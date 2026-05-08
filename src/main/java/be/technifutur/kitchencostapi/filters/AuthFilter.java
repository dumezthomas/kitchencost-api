package be.technifutur.kitchencostapi.filters;

import be.technifutur.kitchencostapi.config.SecurityContextImpl;
import be.technifutur.kitchencostapi.models.auth.UserResponse;
import be.technifutur.kitchencostapi.utils.JwtProvider;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    private JwtProvider jwtProvider;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return;
        }

        try {
            String token = authorization.substring(7);
            if (!jwtProvider.isValid(token)) {
                return;
            }

            UserResponse user = jwtProvider.getUser(token);

            SecurityContext securityContext = new SecurityContextImpl(user);
            requestContext.setSecurityContext(securityContext);
        } catch (Exception e) {

            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}

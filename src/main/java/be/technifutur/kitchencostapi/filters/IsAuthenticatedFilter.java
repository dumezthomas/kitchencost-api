package be.technifutur.kitchencostapi.filters;

import be.technifutur.kitchencostapi.annotations.IsAuthenticated;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHORIZATION)
@IsAuthenticated
public class IsAuthenticatedFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        SecurityContext securityContext = requestContext.getSecurityContext();

        if (securityContext.getUserPrincipal() == null) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}

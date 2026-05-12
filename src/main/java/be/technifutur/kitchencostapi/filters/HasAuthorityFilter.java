package be.technifutur.kitchencostapi.filters;

import be.technifutur.kitchencostapi.annotations.HasAuthority;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.lang.reflect.Method;

@Provider
@Priority(Priorities.AUTHORIZATION)
@HasAuthority
public class HasAuthorityFilter implements ContainerRequestFilter {

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        SecurityContext securityContext = requestContext.getSecurityContext();

        if (securityContext.getUserPrincipal() == null) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        Method method = resourceInfo.getResourceMethod();
        HasAuthority hasAuthority = method.getAnnotation(HasAuthority.class);

        if (!securityContext.isUserInRole(hasAuthority.value())) {
            requestContext.abortWith(
                    Response.status(Response.Status.FORBIDDEN).build());
        }
    }
}

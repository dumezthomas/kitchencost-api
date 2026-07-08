package be.technifutur.kitchencostapi.resources;

import be.technifutur.kitchencostapi.annotations.IsAuthenticated;
import be.technifutur.kitchencostapi.exceptions.InvalidCredentialsException;
import be.technifutur.kitchencostapi.models.auth.LoginRequest;
import be.technifutur.kitchencostapi.services.AuthService;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.Map;

import static be.technifutur.kitchencostapi.utils.Helpers.validate;

@Path("/auth")
public class AuthResource {

    @Inject
    Validator validator;

    @Inject
    AuthService authService;

    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @IsAuthenticated
    public Response me(@Context SecurityContext securityContext) {

        return Response
                .ok(authService.me(securityContext.getUserPrincipal().getName()))
                .build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {

        Map<String, List<String>> errors = validate(validator, request);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errors)
                    .build();
        }

        try {
            return Response
                    .ok(authService.login(request))
                    .build();
        } catch (InvalidCredentialsException e) {

            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }
    }
}

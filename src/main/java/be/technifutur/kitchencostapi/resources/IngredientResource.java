package be.technifutur.kitchencostapi.resources;

import be.technifutur.kitchencostapi.models.ingredient.IngredientPriceUpdateRequest;
import be.technifutur.kitchencostapi.services.IngredientService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Path("/ingredients")
public class IngredientResource {

    @Inject
    Validator validator;

    @Inject
    IngredientService ingredientService;

    @PATCH
    @Path("/{id}/price")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateIngredientPrice(@PathParam("id") Long id, IngredientPriceUpdateRequest request) {

        Set<ConstraintViolation<IngredientPriceUpdateRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            List<String> errors = violations.stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .toList();

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("errors", errors))
                    .build();
        }

        return ingredientService.updateIngredientPrice(id, request)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }
}

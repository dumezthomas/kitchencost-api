package be.technifutur.kitchencostapi.resources;

import be.technifutur.kitchencostapi.services.RecipeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/recipes")
public class RecipeResource {

    @Inject
    RecipeService recipeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipes() {

        return Response
                .ok(recipeService.getRecipes())
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeById(@PathParam("id") Long id) {

        return recipeService.getRecipe(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }
}

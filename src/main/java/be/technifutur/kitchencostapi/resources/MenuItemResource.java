package be.technifutur.kitchencostapi.resources;

import be.technifutur.kitchencostapi.services.MenuItemService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/menu")
public class MenuItemResource {

    @Inject
    MenuItemService menuItemService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenuItems() {

        return Response
                .ok(menuItemService.getAvailableMenuItemsForClient())
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenuItemById(@PathParam("id") Long id) {

        return menuItemService.getAvailableMenuItemForClient(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }
}

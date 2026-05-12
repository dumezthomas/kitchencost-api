package be.technifutur.kitchencostapi.resources;

import be.technifutur.kitchencostapi.annotations.HasAuthority;
import be.technifutur.kitchencostapi.enums.MenuItemStatus;
import be.technifutur.kitchencostapi.services.FinancialReportService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/financial-reports")
public class FinancialReportResource {

    @Inject
    FinancialReportService financialReportService;

    @GET
    @Path("/menu")
    @Produces(MediaType.APPLICATION_JSON)
    @HasAuthority("CHEF")
    public Response getMenuFinancialReport(@QueryParam("status") MenuItemStatus status) {

        return Response
                .ok(financialReportService.getMenuFinancialReport(status))
                .build();
    }

    @GET
    @Path("/menu/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @HasAuthority("CHEF")
    public Response getMenuItemFinancialReport(@PathParam("id") Long id) {

        return financialReportService.getMenuItemFinancialReport(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }
}

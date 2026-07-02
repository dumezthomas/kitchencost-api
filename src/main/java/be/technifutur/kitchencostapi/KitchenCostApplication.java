package be.technifutur.kitchencostapi;

import be.technifutur.kitchencostapi.filters.CorsFilter;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class KitchenCostApplication extends ResourceConfig {

    public KitchenCostApplication() {

        packages("be.technifutur.kitchencostapi");

        register(OpenApiResource.class);
        register(AcceptHeaderOpenApiResource.class);
        
        register(CorsFilter.class);
    }
}
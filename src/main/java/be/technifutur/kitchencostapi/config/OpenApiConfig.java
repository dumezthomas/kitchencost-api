package be.technifutur.kitchencostapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "KitchenCost API",
                version = "1.0",
                description = "API for managing menu, recipes and food cost"
        ),
        servers = @Server(url = "/kitchencost", description = "KitchenCost Api")
)
public class OpenApiConfig {
}

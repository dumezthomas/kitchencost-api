package be.technifutur.kitchencostapi.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;

@ApplicationScoped
public class DataInitializer {

    public void init(@Observes Startup event) {

    }
}
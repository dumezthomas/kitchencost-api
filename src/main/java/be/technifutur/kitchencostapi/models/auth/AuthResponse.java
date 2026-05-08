package be.technifutur.kitchencostapi.models.auth;

public record AuthResponse(

        UserResponse user,
        String token
) {

}

package be.technifutur.kitchencostapi.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {

        super("Invalid credentials");
    }
}

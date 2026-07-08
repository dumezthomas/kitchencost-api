package be.technifutur.kitchencostapi.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import be.technifutur.kitchencostapi.daos.UserDao;
import be.technifutur.kitchencostapi.exceptions.InvalidCredentialsException;
import be.technifutur.kitchencostapi.models.auth.AuthResponse;
import be.technifutur.kitchencostapi.models.auth.LoginRequest;
import be.technifutur.kitchencostapi.models.auth.UserResponse;
import be.technifutur.kitchencostapi.pojos.User;
import be.technifutur.kitchencostapi.utils.JwtProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {

    @Inject
    UserDao userDao;

    @Inject
    JwtProvider jwtProvider;


    public AuthResponse login(LoginRequest request) {

        User user = userDao.findByEmailOrUsername(request.login())
                .orElseThrow(InvalidCredentialsException::new);

        if (!BCrypt.verifyer().verify(request.password().toCharArray(), user.getPassword()).verified)
            throw new InvalidCredentialsException();

        return toResponse(user);
    }

    public UserResponse me(String username) {

        User user = userDao.findByEmailOrUsername(username)
                .orElseThrow(InvalidCredentialsException::new);

        return toUserResponse(user);
    }

    private UserResponse toUserResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getName()
        );
    }

    private AuthResponse toResponse(User user) {

        return new AuthResponse(
                toUserResponse(user),
                jwtProvider.generateToken(user)
        );
    }
}

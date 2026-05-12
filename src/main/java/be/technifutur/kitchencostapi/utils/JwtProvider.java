package be.technifutur.kitchencostapi.utils;

import be.technifutur.kitchencostapi.models.auth.UserResponse;
import be.technifutur.kitchencostapi.pojos.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@ApplicationScoped
public class JwtProvider {

    private static final String SECRET_KEY = "my-super-secret-key-my-super-secret-key";
    private final JwtParser parser;

    public JwtProvider() {

        this.parser = Jwts.parser().verifyWith(getSecretKey()).build();
    }

    public String generateToken(User user) {

        long expiration = 900_000; // 15 minutes (1000ms * 60s * 15m)
        return Jwts.builder()
                .signWith(getSecretKey())
                .id(user.getId().toString())
                .subject(user.getUsername())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public int getId(String token) {

        return Integer.parseInt(getClaims(token).getId());
    }

    public String getUsername(String token) {

        return getClaims(token).getSubject();
    }

    public String getEmail(String token) {

        return getClaims(token).get("email", String.class);
    }

    public String getRole(String token) {

        return getClaims(token).get("role", String.class);
    }

    public boolean isValid(String token) {

        Date now = new Date();
        return getClaims(token).getExpiration().after(now);
    }

    public UserResponse getUser(String token) {

        Integer id = getId(token);
        String username = getUsername(token);
        String email = getEmail(token);
        String role = getRole(token);

        return new UserResponse(id, username, email, role);
    }

    private Claims getClaims(String token) {

        return parser.parseSignedClaims(token).getPayload();
    }

    private SecretKey getSecretKey() {

        byte[] encoded = SECRET_KEY.getBytes(StandardCharsets.UTF_8);

        return new SecretKeySpec(encoded, "HmacSHA256");
    }
}
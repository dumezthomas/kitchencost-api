package be.technifutur.kitchencostapi.daos;

import be.technifutur.kitchencostapi.pojos.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserDao extends CrudDao<User, Integer> {

    public Optional<User> findByEmailOrUsername(String login) {

        try (var em = emf.createEntityManager()) {
            return em.createQuery(
                            "SELECT u FROM User u JOIN FETCH u.role" +
                                    " WHERE u.email = :login OR u.username = :login",
                            User.class)
                    .setParameter("login", login)
                    .getResultStream().findFirst();
        }
    }
}

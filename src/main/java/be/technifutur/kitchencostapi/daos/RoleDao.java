package be.technifutur.kitchencostapi.daos;

import be.technifutur.kitchencostapi.pojos.Role;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RoleDao extends CrudDao<Role, Integer> {

    public Optional<Role> findByName(String name) {
        try (var em = emf.createEntityManager()) {
            List<Role> roles = em.createQuery(
                            "SELECT r FROM Role r WHERE r.name = :name",
                            Role.class)
                    .setParameter("name", name)
                    .getResultList();

            return roles.stream().findFirst();
        }
    }
}

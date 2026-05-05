package be.technifutur.kitchencostapi.daos;

import be.technifutur.kitchencostapi.pojos.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserDao extends CrudDao<User, Integer> {
}

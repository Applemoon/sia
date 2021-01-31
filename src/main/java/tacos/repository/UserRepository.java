package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import tacos.data.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}

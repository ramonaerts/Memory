package rest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import rest.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE username = ?1", nativeQuery = true)
    User findUserByUsername(String username);
}

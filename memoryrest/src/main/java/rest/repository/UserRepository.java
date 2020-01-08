package rest.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import rest.entities.Player;

@Qualifier
public interface UserRepository extends CrudRepository<Player, Integer> {

    @Query(value = "SELECT * FROM player WHERE username = ?1", nativeQuery = true)
    Player findUserByUsername(String username);

    @Query(value = "SELECT * FROM player WHERE username = ?1 AND password = ?2", nativeQuery = true)
    Player findUserByCredentials(String username, String password);
}

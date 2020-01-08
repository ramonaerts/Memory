package restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.entities.Player;
import rest.repository.UserRepository;

@Service
public class RestService {

    @Autowired
    UserRepository repository;

    public Iterable<Player> getAllUsers(){
        return repository.findAll();
    }

    public void addUser(Player playerDTO){
        repository.save(playerDTO);
    }

    public Player getUser(String username){
        return repository.findUserByUsername(username);
    }

    public Player getUserByCredentials(String username, String password){
        return repository.findUserByCredentials(username, password);
    }
}

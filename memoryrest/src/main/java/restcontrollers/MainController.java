package restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.entities.User;
import rest.repository.UserRepository;

@Controller
@RequestMapping(path="/memory")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody void addUser(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setWins(0);
        user.setDraws(0);
        user.setLosses(0);
        user.setScore(0);
        userRepository.save(user);
    }

    @GetMapping(path="/user")
    public @ResponseBody User getUser(String username){
        return userRepository.findUserByUsername(username);
    }
}

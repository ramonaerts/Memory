package restcontrollers;

import interfaces.IRestController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.entities.Player;
import rest.repository.UserRepository;

@Controller
@RequestMapping(path="/memory")
public class RestController implements IRestController {

    @Autowired
    private RestService service;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Player> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping(path="/add")
    public @ResponseBody void addUser(String username, String password){
        Player playerDTO = new Player();
        playerDTO.setUsername(username);
        playerDTO.setPassword(password);
        service.addUser(playerDTO);
    }

    @GetMapping(path="/user")
    public @ResponseBody boolean getCheckUser(String username){
        Player user = service.getUser(username);
        return user.getUsername().equals("");
    }

    @GetMapping(path="/usercred")
    public @ResponseBody models.Player getUserByCredentials(String username, String password){
        Player dtoPlayer = service.getUserByCredentials(username, password);
        if(dtoPlayer == null) return null;
        return modelMapper.map(dtoPlayer, models.Player.class);
    }
}

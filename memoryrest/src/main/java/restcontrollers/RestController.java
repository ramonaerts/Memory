package restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.entities.Player;

@Controller
@RequestMapping(path="/memory")
public class RestController {

    @Autowired
    private RestService service;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Player> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping(path="/adduser")
    public @ResponseBody models.Player addUser(String username, String password){
        Player dtoPlayer = new Player();
        dtoPlayer.setUsername(username);
        dtoPlayer.setPassword(password);
        service.addUser(dtoPlayer);
        return getUserByCredentials(username, password);
    }

    @GetMapping(path="/user")
    public @ResponseBody models.Player getUser(String username){
        Player dtoPlayer = service.getUser(username);
        if (dtoPlayer == null) return null;
        return modelMapper.map(dtoPlayer, models.Player.class);
    }

    @GetMapping(path="/checkusername")
    public @ResponseBody boolean checkUser(String username){
        Player dtoPlayer = service.getUser(username);
        return dtoPlayer == null;
    }

    @GetMapping(path="/usercred")
    public @ResponseBody models.Player getUserByCredentials(String username, String password){
        Player dtoPlayer = service.getUserByCredentials(username, password);
        if(dtoPlayer == null) return null;
        return modelMapper.map(dtoPlayer, models.Player.class);
    }
}

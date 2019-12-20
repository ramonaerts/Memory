package socketcommunication;

import interfaces.IClientMessageGenerator;
import interfaces.IGameClient;
import interfaces.IController;

import java.util.List;

public class GameClient implements IGameClient {

    private IClientMessageGenerator messageGenerator;
    private IController controller;

    public GameClient(IClientMessageGenerator messageGenerator)
    {
        this.messageGenerator = messageGenerator;
    }

    public void registerController(IController controller){
        this.controller = controller;
    }

    @Override
    public void sendPlayer(String username, String password) {
        messageGenerator.loginPlayer(username, password);
    }

    @Override
    public void loginResult(boolean loginresult) {
        controller.loginResult(loginresult);
    }

    @Override
    public void updateLobby(List<String> players)
    {
        controller.updateLobby(players);
    }
}
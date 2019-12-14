package socketcommunication;

import client.ClientMessageGenerator;
import interfaces.IClientMessageGenerator;
import interfaces.IGameClient;
import controllers.IController;

public class GameClient implements IGameClient {

    private IClientMessageGenerator messageGenerator;
    private IController controller;

    public GameClient(IClientMessageGenerator messageGenerator)
    {
        this.messageGenerator = messageGenerator;
    }

    @Override
    public void sendPlayer(String username, String password) {
        messageGenerator.sendPlayer(username, password);
    }

    @Override
    public void receivePlayer(String username) {
        controller.ShowPlayer(username);
    }
}

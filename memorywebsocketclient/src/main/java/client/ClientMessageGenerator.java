package client;

import interfaces.*;
import messages.PlayerLoginMessage;

public class ClientMessageGenerator implements IClientMessageGenerator {
    private IClientWebSocket client;

    public ClientMessageGenerator(IClientWebSocket client){
        this.client = client;
    }

    public void loginPlayer(String username, String password){
        client.send(new PlayerLoginMessage(username, password));
    }
}

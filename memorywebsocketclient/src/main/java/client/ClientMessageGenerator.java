package client;

import interfaces.*;
import messages.PlayerTestMessage;

public class ClientMessageGenerator implements IClientMessageGenerator {
    private IClientWebSocket client;

    public ClientMessageGenerator(IClientWebSocket client){
        this.client = client;
    }

    public void sendPlayer(String username, String password){
        client.send(new PlayerTestMessage(username, password));
    }
}

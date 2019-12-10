package client;

import messages.PlayerTestMessage;

public class ClientMessageGenerator {
    private ClientWebSocket client = ClientWebSocket.getInstance();

    public void sendPlayer(String username, String password){
        client.send(new PlayerTestMessage(username, password));
    }
}

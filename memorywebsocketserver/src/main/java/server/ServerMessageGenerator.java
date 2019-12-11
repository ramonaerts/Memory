package server;

import messages.PlayerTestMessage;
import messages.PlayerTestResultMessage;

public class ServerMessageGenerator {
    private IServerWebSocket server = ServerWebSocket.getInstance();

    public void sendPlayer(String username){
        server.broadcast(new PlayerTestResultMessage(username));
    }
}

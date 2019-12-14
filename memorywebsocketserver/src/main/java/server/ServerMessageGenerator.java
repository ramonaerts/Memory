package server;

import interfaces.*;
import messages.PlayerTestResultMessage;

public class ServerMessageGenerator implements IServerMessageGenerator {
    private IServerWebSocket server;

    public ServerMessageGenerator(IServerWebSocket server) {
        this.server = server;
    }

    public void sendPlayer(String username){
        server.broadcast(new PlayerTestResultMessage(username));
    }
}

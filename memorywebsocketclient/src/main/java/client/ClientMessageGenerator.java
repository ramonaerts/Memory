package client;

import interfaces.*;
import messages.MessageOperation;
import messages.PlayerLoginMessage;
import messages.StartGameMessage;

public class ClientMessageGenerator implements IClientMessageGenerator {
    private IClientWebSocket client;

    public ClientMessageGenerator(IClientWebSocket client){
        this.client = client;
    }

    public void loginPlayer(String username, String password){
        client.send(new PlayerLoginMessage(username, password), MessageOperation.PLAYERLOGIN);
    }

    @Override
    public void startGame() {
        client.send(new StartGameMessage(), MessageOperation.PLAYERSTARTGAME);
    }
}

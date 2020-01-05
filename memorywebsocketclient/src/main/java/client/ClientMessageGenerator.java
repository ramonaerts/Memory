package client;

import interfaces.*;
import messages.*;

public class ClientMessageGenerator implements IClientMessageGenerator {
    private IClientWebSocket client;

    public ClientMessageGenerator(IClientWebSocket client){
        this.client = client;
    }

    public void loginPlayer(String username, String password){
        client.send(new PlayerLoginMessage(username, password), MessageOperation.PLAYERLOGIN);
    }

    public void startGame() {
        client.send(new StartGameMessage(), MessageOperation.PLAYERSTARTGAME);
    }

    public void joinGame(){
        client.send(new JoinGameMessage(), MessageOperation.PLAYERJOINGAME);
    }

    public void turnCard(int x, int y, int gameId){
        client.send(new TurnCardMessage(x, y, gameId),MessageOperation.TURNCARD);
    }
}

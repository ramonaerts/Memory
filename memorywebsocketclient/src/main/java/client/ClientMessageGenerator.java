package client;

import interfaces.*;
import messages.*;

public class ClientMessageGenerator implements IClientMessageGenerator {
    private IClientWebSocket client;

    public ClientMessageGenerator(IClientWebSocket client){
        this.client = client;
    }

    public void loginPlayer(String username, String password){
        client.send(new PlayerAuthenticationMessage(username, password), MessageOperation.PLAYERLOGIN);
    }

    public void logOutPlayer() {
        client.send(new LogOutMessage(), MessageOperation.LOGOUT);
    }

    public void registerPlayer(String username, String password){
        client.send(new PlayerAuthenticationMessage(username, password), MessageOperation.PLAYERREGISTER);
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

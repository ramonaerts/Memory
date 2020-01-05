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

    public void sendPlayer(String username, String password) {
        messageGenerator.loginPlayer(username, password);
    }

    public void loginResult(boolean loginresult, Object player) {
        controller.loginResult(loginresult, player);
    }

    public void updateLobby(List<String> players) {
        controller.updateLobby(players);
    }

    public void startGame(){
        messageGenerator.startGame();
    }

    public void startGameResult(boolean startResult, int gameId){
        controller.startGameResult(startResult, gameId);
    }

    public void joinGame() {
        messageGenerator.joinGame();
    }

    public void joinGameResult(boolean joinResult, int gameId, Object opponent) {
        controller.joinGameResult(joinResult, gameId, opponent);
    }

    public void playerJoinsGame(Object opponent) {
        controller.playerJoinsGame(opponent);
    }

    public void turnCard(int x, int y, int gameId){
        messageGenerator.turnCard(x, y, gameId);
    }
}

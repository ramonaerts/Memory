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

    public void loginResult(boolean loginresult) {
        controller.loginResult(loginresult);
    }

    public void updateLobby(List<String> players) {
        controller.updateLobby(players);
    }

    public void startGame(){
        messageGenerator.startGame();
    }

    public void startGameResult(boolean startResult){
        controller.startGameResult(startResult);
    }

    public void joinGame() {
        messageGenerator.joinGame();
    }

    public void joinGameResult(boolean joinResult, String opponentName) {
        controller.joinGameResult(joinResult, opponentName);
    }

    public void playerJoinsGame(String opponentName) {
        controller.playerJoinsGame(opponentName);
    }
}

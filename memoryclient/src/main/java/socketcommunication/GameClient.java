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

    public void logOutPlayer() {
        messageGenerator.logOutPlayer();
    }

    public void registerPlayer(String username, String password) {
        messageGenerator.registerPlayer(username, password);
    }

    public void registerResult(boolean registerResult, Object player){
        controller.registerResult(registerResult, player);
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

    public void showCardInfo(int cardValue, Object coordinate, int playerNr){
        controller.showCardInfo(cardValue, coordinate, playerNr);
    }

    public void turnCardBack(Object coordinate){
        controller.turnCardBack(coordinate);
    }

    public void sendPoint(int playerNr){
        controller.sendPoint(playerNr);
    }

    public void gameResult(Object result) {
        controller.gameResult(result);
    }

    public void feedback(String message){
        controller.feedback(message);
    }
}

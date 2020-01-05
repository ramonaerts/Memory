package controllers;

import interfaces.IController;
import interfaces.IGameClient;
import gui.IMemoryGui;
import gui.Memory;

import java.util.List;

public class MemoryController implements IController {

    private IMemoryGui gui;
    private IGameClient client;

    public MemoryController(Memory gui, IGameClient client){
        this.client = client;
        client.registerController(this);
        this.gui = gui;
    }

    public void loginPlayer(String username, String password) {
        client.sendPlayer(username, password);
    }

    public void loginResult(boolean loginresult, Object player){
        gui.loginResult(loginresult, player);
    }

    public void updateLobby(List<String> players){
        gui.updateLobby(players);
    }

    public void startGame() {
        client.startGame();
    }

    public void startGameResult(boolean startResult, int gameId) {
        gui.startGameResult(startResult, gameId);
    }

    public void joinGame(){
        client.joinGame();
    }

    public void joinGameResult(boolean joinResult, int gameId, Object opponent){
        gui.joinGameResult(joinResult, gameId, opponent);
    }

    public void playerJoinsGame(Object opponent){
        gui.playerJoinsGame(opponent);
    }

    public void turnCard(int x, int y, int gameId){
        client.turnCard(x, y, gameId);
    }

    public void showCardInfo(int cardValue, Object coordinate, int playerNr){
        gui.showCardInfo(cardValue, coordinate, playerNr);
    }

    public void turnCardBack(Object coordinate){
        gui.turnCardBack(coordinate);
    }

    public void sendPoint(int playerNr){
        gui.sendPoint(playerNr);
    }

    public void feedback(String message){
        gui.messageToGameChat(message);
    }
}

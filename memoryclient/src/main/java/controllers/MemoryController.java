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

    public void loginResult(boolean loginresult){
        gui.loginResult(loginresult);
    }

    public void updateLobby(List<String> players){
        gui.updateLobby(players);
    }

    public void startGame() {
        client.startGame();
    }

    public void startGameResult(boolean startResult) {
        gui.startGameResult(startResult);
    }

    public void joinGame(){
        client.joinGame();
    }

    public void joinGameResult(boolean joinResult, String opponentName){
        gui.joinGameResult(joinResult, opponentName);
    }

    public void playerJoinsGame(String opponentName){
        gui.playerJoinsGame(opponentName);
    }
}

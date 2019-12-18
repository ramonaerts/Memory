package controllers;

import interfaces.IController;
import interfaces.IGameClient;
import gui.IMemoryGui;
import gui.Memory;

public class MemoryController implements IController {

    private IMemoryGui gui;
    private IGameClient client;

    public MemoryController(Memory gui, IGameClient client){
        this.client = client;
        client.registerController(this);
        this.gui = gui;
    }

    @Override
    public void loginPlayer(String username, String password) {
        client.sendPlayer(username, password);
    }

    @Override
    public void loginResult(boolean loginresult){
        gui.loginResult(loginresult);
    }

    @Override
    public void updateLobby(String username){
        gui.updateLobby(username);
    }
}

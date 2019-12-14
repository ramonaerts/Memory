package controllers;

import interfaces.IGameClient;
import gui.IMemoryGui;
import gui.Memory;
import socketcommunication.GameClient;

public class MemoryController implements IController {

    private IMemoryGui gui;
    private IGameClient client;

    public MemoryController(Memory gui, IGameClient client){
        this.client = client;
        this.gui = gui;
    }

    @Override
    public void RegisterPlayer(String username, String password) {
        client.sendPlayer(username, password);
    }

    @Override
    public void ShowPlayer(String username){
        gui.showPlayer(username);
    }
}

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
    public void RegisterPlayer(String username, String password) {
        client.sendPlayer(username, password);
    }

    @Override
    public void ShowPlayer(String username){
        String test = "oi";
        gui.showPlayer(username);
    }
}

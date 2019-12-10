package controllers;

import gui.IMemoryGui;
import gui.Memory;
import models.Player;
import socketcommunication.*;

public class MemoryController implements IMemoryController {

    private IMemoryGui gui;
    private IGameClient client;

    public MemoryController(Memory gui){
        this.gui = gui;
        client = new GameClient();
    }

    @Override
    public void RegisterPlayer(String username, String password) {
        client.sendPlayer(username, password);
    }
}

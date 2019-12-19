package logic;

import enums.GameState;
import interfaces.*;
import models.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryLogic implements IGameLogic {
    private int gameID;
    private List<Player> onlinePlayers = new ArrayList<>();
    private int playerAmount;
    private Card[][] cards = new Card[6][3];
    private int round;
    boolean singlePlayer;

    private IServerMessageGenerator generator;

    public MemoryLogic(IServerMessageGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void loginPlayer(String username, String password, String sessionId)
    {
        Player player = new Player(username, password, sessionId);
        onlinePlayers.add(player);

/*        if(player.getUsername().equals("test") && player.getPassword().equals("aerts"))
        {
            generator.sendPlayerResult(true);
        }
        else generator.sendPlayerResult(false);*/

        generator.sendPlayerResult(true, player.getSessionID());
        updateLobby();

    }

    private void updateLobby()
    {
        List<String> playernames = new ArrayList<>();

        for (Player player : onlinePlayers) {
            playernames.add(player.getUsername());
        }

        for (Player onlineplayer : onlinePlayers)
        {
            if (onlineplayer.getGameState() == GameState.LOBBY) generator.updateLobbyList(playernames, onlineplayer.getSessionID());
        }
    }
}
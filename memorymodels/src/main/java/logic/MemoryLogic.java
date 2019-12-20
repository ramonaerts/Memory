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
    private List<Player> activeGames = new ArrayList<>();
    private int playerAmount;
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

    public void startGame(String sessionId)
    {
        Player player = getPlayer(sessionId);
        Game game = new Game();
        game.playerStartsGame(player);
        activeGames.add(player);
    }

    private Player getPlayer(String sessionId)
    {
        for (Player player : onlinePlayers)
        {
            if (player.getSessionID() == sessionId) return player;
        }
        return null;
    }

    private void updateLobby()
    {
        List<String> playernames = new ArrayList<>();

        for (Player player : onlinePlayers) {
            playernames.add(player.getUsername());
        }

        for (Player player : onlinePlayers)
        {
            if (player.getGameState() == GameState.LOBBY) generator.updateLobbyList(playernames, player.getSessionID());
        }
    }
}
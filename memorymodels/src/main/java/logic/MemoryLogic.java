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
    private List<Game> activeGames = new ArrayList<>();
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

        generator.sendPlayerResult(true, player.getSessionID());
        updateLobby();
    }

    public void startGame(String sessionId)
    {
        Player player = getPlayer(sessionId);

        Game game = new Game();
        game.playerStartsGame(player);
        activeGames.add(game);

        if (player != null)
        {
            updatePlayerGameState(player, GameState.PLAYING);
            generator.sendGameStartResult(true, sessionId);
        }
    }

    public void joinGame(String sessionId)
    {
        Player player = getPlayer(sessionId);

        for (Game game : activeGames) {
            if (game.getPlayeramount() == 1)
            {
                game.playerJoinsGame(player);
                generator.sendGameJoinResult(true, game.getPlayer1().getUsername(), sessionId);
                if (player != null) generator.playerJoinsGame(player.getUsername(), game.getPlayer1().getSessionID());
                return;
            }
        }
        generator.sendGameJoinResult(false, null, sessionId);
    }

    private Player getPlayer(String sessionId)
    {
        for (Player player : onlinePlayers)
        {
            if (player.getSessionID() == sessionId) return player;
        }
        return null;
    }

    private void updatePlayerGameState(Player player, GameState gameState)
    {
        for (Player onlineplayer : onlinePlayers)
        {
            if (onlineplayer.getSessionID().equals(player.getSessionID())) onlineplayer.setGameState(gameState);
        }
        updateLobby();
    }

    private void updateLobby()
    {
        List<String> playernames = new ArrayList<>();

        for (Player player : onlinePlayers)
        {
            if (player.getGameState() == GameState.LOBBY) playernames.add(player.getUsername());
        }

        for (Player player : onlinePlayers)
        {
            if (player.getGameState() == GameState.LOBBY) generator.updateLobbyList(playernames, player.getSessionID());
        }
    }
}
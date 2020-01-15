package logic;

import enums.GameResult;
import enums.GameState;
import interfaces.*;
import models.*;
import java.util.ArrayList;
import java.util.List;

public class MemoryLogic implements IGameLogic {
    public List<Player> onlinePlayers = new ArrayList<>();
    private List<Game> activeGames = new ArrayList<>();

    private IServerMessageGenerator generator;
    private IRestClient restClient;

    public MemoryLogic(IServerMessageGenerator generator, IRestClient restClient) {
        this.generator = generator;
        this.restClient = restClient;
    }

    @Override
    public void loginPlayer(String username, String password, String sessionId)
    {
        Player player = (Player) restClient.getPlayerByCredentials(username, password);

        if(player != null){
            player.setSessionID(sessionId);
            generator.sendPlayerResult(true, player, player.getSessionID());
            onlinePlayers.add(player);
        }

        else generator.sendPlayerResult(false, null, sessionId);

        updateLobby();
    }

    public void registerPlayer(String username, String password, String sessionId) {
        boolean usernameExists = restClient.checkUsername(username);

        if(usernameExists){
            Player player = (Player) restClient.registerPlayer(username, password);
            player.setSessionID(sessionId);
            generator.sendRegisterResult(true, player, sessionId);
            onlinePlayers.add(player);
        }

        else generator.sendRegisterResult(false, null, sessionId);

        updateLobby();
    }

    public void startGame(String sessionId)
    {
        Player player = getPlayer(sessionId);
        assert player != null;

        Game game = new Game(this.generator, this);
        game.setGameID(createGameID());
        player.setInGameNr(1);
        game.playerStartsGame(player);
        activeGames.add(game);

        updatePlayerGameState(player, GameState.PLAYING);
        generator.sendGameStartResult(true, game.getGameID(), sessionId);

    }

    private int createGameID(){
        return activeGames.size() + 1;
    }

    public void joinGame(String sessionId)
    {
        Player player = getPlayer(sessionId);
        assert player != null;

        for (Game game : activeGames) {
            if (game.getPlayersInGame().size() != 2)
            {
                updatePlayerGameState(player, GameState.PLAYING);
                player.setInGameNr(game.getPlayersInGame().size() + 1);
                game.playerJoinsGame(player);
                generator.sendGameJoinResult(true, game.getGameID(),  game.getOpponent(sessionId), sessionId);
                generator.playerJoinsGame(player, game.getOpponent(sessionId).getSessionID());
                return;
            }
        }
        generator.sendGameJoinResult(false, 0, null, sessionId);
    }

    public Player getPlayer(String sessionId)
    {
        for (Player player : onlinePlayers)
        {
            if (player.getSessionID().equals(sessionId)) return player;
        }
        return null;
    }

    private Game getGame(int gameId){
        for (Game game : activeGames) {
            if (game.getGameID() == gameId) return game;
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
        List<String> playerNames = new ArrayList<>();

        for (Player player : onlinePlayers)
        {
            if (player.getGameState() == GameState.LOBBY) playerNames.add(player.getUsername());
        }

        for (Player player : onlinePlayers)
        {
            if (player.getGameState() == GameState.LOBBY) generator.updateLobbyList(playerNames, player.getSessionID());
        }
    }

    public void turnCard(int x, int y, int gameId, String sessionId)
    {
        for (Game game : activeGames)
        {
            if (game.getGameID() == gameId) game.playerTurnsCard(sessionId, x, y);
        }
    }

    public void saveResultsToDatabase(String sessionId){
        Player player = getPlayer(sessionId);

        assert player != null;
        if (player.getGameResult() == GameResult.WIN) player.setWins(player.getWins()+1);
        if (player.getGameResult() == GameResult.DRAW) player.setDraws(player.getDraws()+1);
        if (player.getGameResult() == GameResult.LOSE) player.setLosses(player.getLosses()+1);
    }
}
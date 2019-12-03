package models;

import enums.GameState;

public class Player {

    private int playerID;
    private String username;
    private String password;
    private int sessionID;
    private Stats stats;
    private int points;
    private GameState gameState;

    public Player(String username, String password, int playerid)
    {
        this.username = username;
        this.password = password;
        this.playerID = playerid;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}

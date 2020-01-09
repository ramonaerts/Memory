package models;

import enums.GameResult;
import enums.GameState;

public class Player {

    private int playerID;
    private String username;
    private String password;
    private String sessionID;
    private int wins;
    private int draws;
    private int losses;
    private int score;
    private int points;
    private int turnAmount;
    private int inGameNr;
    private GameState gameState;
    private GameResult gameResult;
    private boolean isAbleToPlay;

    public Player(String username, String password, String sessionid)
    {
        this.username = username;
        this.password = password;
        this.sessionID = sessionid;
        this.gameState = GameState.LOBBY;
    }

    public Player() {
        this.gameState = GameState.LOBBY;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTurnAmount() {
        return turnAmount;
    }

    public void setTurnAmount(int turnAmount) {
        this.turnAmount = turnAmount;
    }

    public int getInGameNr()
    {
        return inGameNr;
    }

    public void setInGameNr(int inGameNr)
    {
        this.inGameNr = inGameNr;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public boolean getIsAbleToPlay() {
        return isAbleToPlay;
    }

    public void setAbleToPlay(boolean ableToPlay) {
        isAbleToPlay = ableToPlay;
    }
}

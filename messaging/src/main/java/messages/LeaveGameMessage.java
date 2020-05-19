package messages;

public class LeaveGameMessage {
    private int gameId;

    public LeaveGameMessage(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}

package messages;

public class TurnCardMessage {
    private int x;
    private int y;
    private int gameId;

    public TurnCardMessage(int x, int y, int gameId) {
        this.x = x;
        this.y = y;
        this.gameId = gameId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getGameId(){
        return gameId;
    }
}

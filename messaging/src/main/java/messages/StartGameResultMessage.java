package messages;

public class StartGameResultMessage {
    private boolean startresult;
    private int gameId;

    public StartGameResultMessage(boolean startresult, int gameId) {
        this.startresult = startresult;
        this.gameId = gameId;
    }

    public boolean getResult() {
        return startresult;
    }

    public int getGameId(){
        return gameId;
    }
}

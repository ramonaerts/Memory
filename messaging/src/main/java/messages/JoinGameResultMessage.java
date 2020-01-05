package messages;

import models.Player;

public class JoinGameResultMessage {

    private boolean joinresult;
    private int gameId;
    private Player opponent;

    public JoinGameResultMessage(boolean joinresult, int gameId, Object opponent) {
        this.joinresult = joinresult;
        this.gameId = gameId;
        this.opponent = (Player)opponent;
    }

    public boolean getResult() {
        return joinresult;
    }

    public int getGameId() {
        return gameId;
    }

    public Player getOpponent() {
        return opponent;
    }
}

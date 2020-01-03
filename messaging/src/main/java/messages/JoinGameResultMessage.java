package messages;

import models.Player;

public class JoinGameResultMessage {

    private boolean joinresult;
    private Player opponent;

    public JoinGameResultMessage(boolean joinresult, Object opponent) {
        this.joinresult = joinresult;
        this.opponent = (Player)opponent;
    }

    public boolean getResult() {
        return joinresult;
    }

    public Player getOpponent() {
        return opponent;
    }
}

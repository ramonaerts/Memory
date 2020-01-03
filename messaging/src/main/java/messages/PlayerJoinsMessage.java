package messages;

import models.Player;

public class PlayerJoinsMessage {
    private Player opponent;

    public PlayerJoinsMessage(Object opponent) {
        this.opponent = (Player) opponent;
    }

    public Player getOpponent() {
        return opponent;
    }
}

package messages;

import models.Player;

public class PlayerLoginResultMessage {
    private boolean loginresult;
    private Player player;

    public PlayerLoginResultMessage(boolean loginresult, Object player) {
        this.loginresult = loginresult;
        this.player = (Player) player;
    }

    public boolean getResult() {
        return loginresult;
    }

    public Player getPlayer() {return player;}
}

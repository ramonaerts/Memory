package messages;

public class SendPointMessage {
    private int playerNr;

    public SendPointMessage(int playerNr) {
        this.playerNr = playerNr;
    }

    public int getPlayerNr() {
        return playerNr;
    }
}

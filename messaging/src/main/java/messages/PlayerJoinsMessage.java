package messages;

public class PlayerJoinsMessage {
    private String opponentname;

    public PlayerJoinsMessage(String opponentname) {
        this.opponentname = opponentname;
    }

    public String getOpponentname() {
        return opponentname;
    }
}

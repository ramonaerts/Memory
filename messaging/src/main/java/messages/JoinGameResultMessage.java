package messages;

public class JoinGameResultMessage {

    private boolean joinresult;
    private String opponentname;

    public JoinGameResultMessage(boolean joinresult, String opponentname) {
        this.joinresult = joinresult;
        this.opponentname = opponentname;
    }

    public boolean getResult() {
        return joinresult;
    }

    public String getOpponentname() {
        return opponentname;
    }
}

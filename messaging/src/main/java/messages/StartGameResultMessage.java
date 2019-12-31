package messages;

public class StartGameResultMessage {
    private boolean startresult;

    public StartGameResultMessage(boolean startresult) {
        this.startresult = startresult;
    }

    public boolean getResult() {
        return startresult;
    }
}

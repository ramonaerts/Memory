package messages;

public class PlayerLoginResultMessage {
    private boolean loginresult;

    public PlayerLoginResultMessage(boolean loginresult) {
        this.loginresult = loginresult;
    }

    public boolean getResult() {
        return loginresult;
    }
}

package messages;

public class PlayerTestResultMessage {
    private String username;

    public PlayerTestResultMessage(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

package messages;

public class PlayerLoginMessage {

    private String username;
    private String password;

    public PlayerLoginMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

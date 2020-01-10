package messages;

public class PlayerAuthenticationMessage {

    private String username;
    private String password;

    public PlayerAuthenticationMessage(String username, String password) {
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

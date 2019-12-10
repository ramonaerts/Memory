package messages;

import models.Player;

public class PlayerTestMessage {

    private String username;
    private String password;

    public PlayerTestMessage(String username, String password) {
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

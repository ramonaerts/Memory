package messages;

public class UpdateLobbyPlayersMessage {
    private String playername;

    public UpdateLobbyPlayersMessage(String playername) {
        this.playername = playername;
    }

    public String getPlayerName() {
        return playername;
    }
}

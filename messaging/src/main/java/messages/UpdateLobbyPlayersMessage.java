package messages;

import java.util.List;

public class UpdateLobbyPlayersMessage {
    private List<String> players;

    public UpdateLobbyPlayersMessage(List<String> players) {
        this.players = players;
    }

    public List<String> getPlayerListName() {
        return players;
    }
}

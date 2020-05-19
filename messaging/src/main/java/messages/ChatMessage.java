package messages;

public class ChatMessage {
    private String message;
    private int gameId;

    public ChatMessage(String message, int gameId) {
        this.message = message;
        this.gameId = gameId;
    }

    public String getMessage() {
        return message;
    }

    public int getGameId() {
        return gameId;
    }
}

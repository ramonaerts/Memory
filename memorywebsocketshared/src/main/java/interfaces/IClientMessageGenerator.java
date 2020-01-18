package interfaces;

public interface IClientMessageGenerator {
    void loginPlayer(String username, String password);
    void logOutPlayer();
    void registerPlayer(String username, String password);
    void startGame();
    void joinGame();
    void leaveGame(int gameId);
    void sendChatMessage(String message, int gameId);
    void turnCard(int x, int y, int gameId);
}

package interfaces;

import java.util.List;

public interface IGameClient {
    void registerController(IController controller);

    void sendPlayer(String username, String password);
    void loginResult(boolean loginResult, Object player);

    void logOutPlayer();

    void registerPlayer(String username, String password);
    void registerResult(boolean registerResult, Object player);

    void updateLobby(List<String> players);

    void startGame();
    void startGameResult(boolean startResult, int gameId);

    void joinGame();
    void joinGameResult(boolean joinResult, int gameId, Object opponent);
    void playerJoinsGame(Object opponent);
    void leaveGame(int gameId);

    void sendChatMessage(String message, int gameId);
    void turnCard(int x, int y, int gameId);
    void showCardInfo(int cardValue, Object coordinate, int playerNr);
    void turnCardBack(Object coordinate);
    void sendPoint(int playerNr);

    void gameResult(Object result);

    void feedback(String message);
}

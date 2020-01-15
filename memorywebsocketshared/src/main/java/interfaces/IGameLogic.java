package interfaces;

import java.util.List;

public interface IGameLogic {

    void loginPlayer(String username, String password, String sessionId);
    void registerPlayer(String username, String password, String sessionId);
    void startGame(String sessionId);
    void joinGame(String sessionId);
    void turnCard(int x, int y, int gameId, String sessionId);
    void saveResults(String sessionId);
}

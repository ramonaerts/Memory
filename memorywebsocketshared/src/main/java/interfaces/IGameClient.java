package interfaces;

import java.util.List;

public interface IGameClient {
    void registerController(IController controller);

    void sendPlayer(String username, String password);

    void loginResult(boolean loginResult, Object player);
    void updateLobby(List<String> players);

    void startGame();
    void startGameResult(boolean startResult);

    void joinGame();
    void joinGameResult(boolean joinResult, Object opponent);
    void playerJoinsGame(Object opponent);
}

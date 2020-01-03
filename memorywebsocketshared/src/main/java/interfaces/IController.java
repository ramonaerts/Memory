package interfaces;

import java.util.List;

public interface IController {
    void loginPlayer(String username, String password);
    void loginResult(boolean loginresult, Object player);

    void updateLobby(List<String> players);

    void startGame();
    void startGameResult(boolean startResult);

    void joinGame();
    void joinGameResult(boolean joinResult, Object opponent);
    void playerJoinsGame(Object opponent);
}

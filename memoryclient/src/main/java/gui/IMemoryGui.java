package gui;

import java.util.List;

public interface IMemoryGui {
    //void registerPlayer(String username, String password);
    void loginResult(boolean loginresult, Object player);
    void updateLobby(List<String> players);
    void startGameResult(boolean startResult);
    void joinGameResult(boolean joinResult, Object opponent);
    void playerJoinsGame(Object opponent);
}

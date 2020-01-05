package gui;

import java.util.List;

public interface IMemoryGui {
    //void registerPlayer(String username, String password);
    void loginResult(boolean loginresult, Object player);
    void updateLobby(List<String> players);
    void startGameResult(boolean startResult, int gameId);
    void joinGameResult(boolean joinResult, int gameId, Object opponent);
    void playerJoinsGame(Object opponent);
    void showCardInfo(int cardValue, Object coordinate, boolean firstPlayer);
    void messageToGameChat(String message);
}

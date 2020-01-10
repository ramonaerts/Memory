package gui;

import java.util.List;

public interface IMemoryGui {
    void loginResult(boolean loginResult, Object player);
    void registerResult(boolean registerResult, Object player);
    void updateLobby(List<String> players);
    void startGameResult(boolean startResult, int gameId);
    void joinGameResult(boolean joinResult, int gameId, Object opponent);
    void playerJoinsGame(Object opponent);
    void showCardInfo(int cardValue, Object coordinate, int playerNr);
    void turnCardBack(Object coordinate);
    void sendPoint(int playerNr);
    void gameResult(Object result);
    void messageToGameChat(String message);
}

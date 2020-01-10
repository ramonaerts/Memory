package interfaces;

import java.util.List;

public interface IServerMessageGenerator {
    void sendPlayerResult(boolean loginResult, Object player, String sessionId);
    void sendRegisterResult(boolean registerResult, Object player, String sessionId);
    void updateLobbyList(List<String> players, String sessionId);
    void sendGameStartResult(boolean startResult, int gameId, String sessionId);
    void sendGameJoinResult(boolean joinResult, int gameId, Object opponent, String sessionId);
    void playerJoinsGame(Object opponent, String sessionId);
    void sendCardInfo(int cardValue, Object coordinate, int playerNr, String sessionId);
    void turnCardBack(Object coordinate, String sessionId);
    void sendPointMessage(int playerNr, String sessionId);
    void sendGameFeedback(String message, String sessionId);
    void sendGameResult(Object result, String sessionId);
}

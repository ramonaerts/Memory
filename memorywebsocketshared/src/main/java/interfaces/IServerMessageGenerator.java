package interfaces;

import java.util.List;

public interface IServerMessageGenerator {
    void sendPlayerResult(boolean loginResult, Object player, String sessionId);
    void updateLobbyList(List<String> players, String sessionId);
    void sendGameStartResult(boolean startResult, int gameId, String sessionId);
    void sendGameJoinResult(boolean joinResult, int gameId, Object opponent, String sessionId);
    void playerJoinsGame(Object opponent, String sessionId);
    void sendGameFeedback(String message, String sessionId);
}

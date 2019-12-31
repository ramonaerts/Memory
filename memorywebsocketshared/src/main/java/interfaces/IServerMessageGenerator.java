package interfaces;

import java.util.List;

public interface IServerMessageGenerator {
    void sendPlayerResult(boolean loginResult, String sessionId);
    void updateLobbyList(List<String> players, String sessionId);
    void sendGameStartResult(boolean startResult, String sessionId);
}

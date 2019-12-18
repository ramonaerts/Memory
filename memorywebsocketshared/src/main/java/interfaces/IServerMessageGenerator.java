package interfaces;

public interface IServerMessageGenerator {
    void sendPlayerResult(boolean loginResult, String sessionId);
    void updateLobbyList(String username, String sessionId);
}

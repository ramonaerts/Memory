package interfaces;

public interface IServerMessageGenerator {
    void sendPlayerResult(boolean loginResult);
    void updateLobbyList(String username, String sessionId);
}

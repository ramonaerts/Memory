package interfaces;

public interface IServerMessageProcessor {
    void processMessage(String sessionId, String type, String data);
    void registerGame(IGame game);
}

package interfaces;

public interface IServerMessageProcessor {
    void processMessage(String sessionId, Object operation, String data);
    void registerGame(IGameLogic game);
}

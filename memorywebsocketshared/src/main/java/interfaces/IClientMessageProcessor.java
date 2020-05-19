package interfaces;

public interface IClientMessageProcessor {
    void registerGameClient(IGameClient gameClient);
    void processMessage(String sessionId, Object operation, String data);
}

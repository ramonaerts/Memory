package interfaces;

public interface IClientMessageProcessor {
    void processMessage(String sessionId, Object operation, String data);
    void registerGameClient(IGameClient gameClient);
}

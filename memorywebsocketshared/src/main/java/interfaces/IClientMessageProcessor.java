package interfaces;

public interface IClientMessageProcessor {
    void processMessage(String sessionId, String type, String data);
    void registerGameClient(IGameClient gameClient);
}

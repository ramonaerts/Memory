package interfaces;

public interface IServerWebSocket {
    void setMessageProcessor(IServerMessageProcessor processor);

    void sendTo(String sessionId, Object object);

    void broadcast(Object object);

    void sendToOthers(String sessionId, Object object);
}

package interfaces;

public interface IServerWebSocket {
    void setMessageProcessor(IServerMessageProcessor processor);

    void sendTo(String sessionId, Object object, Object operation);

    void broadcast(Object object, Object operation);

    void sendToOthers(String sessionId, Object object, Object operation);
}

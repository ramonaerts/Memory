package interfaces;

public interface IClientWebSocket {
    void start();

    void stop();

    void send(Object object);

    void onWebSocketMessageReceived(String message, String sessionId);

    void setMessageProcessor(IClientMessageProcessor processor);
}

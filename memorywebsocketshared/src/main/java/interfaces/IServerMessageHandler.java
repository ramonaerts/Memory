package interfaces;

public interface IServerMessageHandler {

    void handleMessage(String message, String sessionId);
}

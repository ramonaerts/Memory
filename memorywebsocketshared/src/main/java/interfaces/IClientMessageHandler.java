package interfaces;

public interface IClientMessageHandler {

    void handleMessage(String message, String sessionId);
}

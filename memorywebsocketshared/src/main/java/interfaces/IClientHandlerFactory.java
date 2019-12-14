package interfaces;

public interface IClientHandlerFactory {
    IClientMessageHandler getHandler(String simpleType, Object game);
}

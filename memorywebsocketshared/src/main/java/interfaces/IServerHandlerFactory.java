package interfaces;

public interface IServerHandlerFactory {
    IServerMessageHandler getHandler(String simpleType, Object game);
}

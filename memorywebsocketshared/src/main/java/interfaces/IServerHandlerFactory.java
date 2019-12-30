package interfaces;

public interface IServerHandlerFactory {
    IServerMessageHandler getHandler(Object operation, Object game);
}

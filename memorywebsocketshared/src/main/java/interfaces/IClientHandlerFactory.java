package interfaces;

public interface IClientHandlerFactory {
    IClientMessageHandler getHandler(Object operation, Object game);
}

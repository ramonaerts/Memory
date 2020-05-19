package client;

import interfaces.IClientHandlerFactory;
import interfaces.IClientMessageProcessor;
import interfaces.IGameClient;
import interfaces.IClientMessageHandler;

public class ClientMessageProcessor implements IClientMessageProcessor {

    private IGameClient gameClient;
    private IClientHandlerFactory factory;

    public ClientMessageProcessor(IClientHandlerFactory factory) {
        this.factory = factory;
    }

    public void registerGameClient(IGameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void processMessage(String sessionId, Object operation, String data) {
        IClientMessageHandler handler = factory.getHandler(operation, gameClient);
        handler.handleMessage(data, sessionId);
    }



}

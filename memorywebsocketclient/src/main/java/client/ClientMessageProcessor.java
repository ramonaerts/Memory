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

    public void processMessage(String sessionId, String type, String data) {
        String simpleType = type.split("\\.")[type.split("\\.").length - 1];

        IClientMessageHandler handler = factory.getHandler(simpleType, gameClient);
        handler.handleMessage(data, sessionId);
    }



}

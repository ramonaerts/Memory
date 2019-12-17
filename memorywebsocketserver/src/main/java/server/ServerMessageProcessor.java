package server;

import interfaces.*;

public class ServerMessageProcessor implements IServerMessageProcessor {

    private IGameLogic game;
    private IServerHandlerFactory factory;

    public void registerGame(IGameLogic game){
        this.game = game;
    }

    public ServerMessageProcessor(IServerHandlerFactory factory) {
        this.factory = factory;
    }

    public void processMessage(String sessionId, String type, String data) {
        String simpleType = type.split("\\.")[type.split("\\.").length - 1];

        IServerMessageHandler handler = factory.getHandler(simpleType, game);
        handler.handleMessage(data, sessionId);
    }
}

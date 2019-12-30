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

    public void processMessage(String sessionId, Object operation, String data) {
        IServerMessageHandler handler = factory.getHandler(operation, game);
        handler.handleMessage(data, sessionId);
    }
}

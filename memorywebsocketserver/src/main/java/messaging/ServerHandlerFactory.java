package messaging;

import interfaces.IGameLogic;
import interfaces.IServerHandlerFactory;
import interfaces.IServerMessageHandler;
import messages.MessageOperation;

public class ServerHandlerFactory implements IServerHandlerFactory {

    public IServerMessageHandler getHandler(Object operation, Object game)
    {
        IGameLogic igame = (IGameLogic) game;
        MessageOperation messageOperation = (MessageOperation) operation;

        switch (messageOperation){
            case PLAYERLOGIN:
                return new LoginHandler(igame);
            case PLAYERSTARTGAME:
                return new StartGameHandler(igame);
            default:
                return null;
        }
    }
}

package messaging;

import interfaces.IClientHandlerFactory;
import interfaces.IClientMessageHandler;
import interfaces.IGameClient;
import messages.MessageOperation;

public class ClientHandlerFactory implements IClientHandlerFactory {

    public IClientMessageHandler getHandler(Object operation, Object gameclient)
    {
        IGameClient igameClient = (IGameClient) gameclient;
        MessageOperation messageOperation = (MessageOperation) operation;
        
        switch (messageOperation){
            case PLAYERLOGINRESULT:
                return new LoginResultHandler(igameClient);
            case UPDATELOBBY:
                return new UpdateLobbyResultHandler(igameClient);
            default:
                return null;
        }
    }
}

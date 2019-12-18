package messaging;

import interfaces.IClientHandlerFactory;
import interfaces.IClientMessageHandler;
import interfaces.IGameClient;

public class ClientHandlerFactory implements IClientHandlerFactory {

    public IClientMessageHandler getHandler(String classname, Object gameclient)
    {
        IGameClient igameClient = (IGameClient) gameclient;
        switch (classname){
            case "PlayerLoginResultMessage":
                return new LoginResultHandler(igameClient);
            default:
                return null;
        }
    }
}

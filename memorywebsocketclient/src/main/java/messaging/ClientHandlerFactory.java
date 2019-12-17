package messaging;

import interfaces.IClientHandlerFactory;
import interfaces.IClientMessageHandler;
import interfaces.IGameClient;

public class ClientHandlerFactory implements IClientHandlerFactory {

    public IClientMessageHandler getHandler(String classname, Object gameclient)
    {
        IGameClient igameClient = (IGameClient) gameclient;
        switch (classname){
            case "PlayerTestResultMessage":
                return new TestResultHandler(igameClient);
            default:
                return null;
        }
    }
}

package messaging;

import interfaces.IGameLogic;
import interfaces.IServerHandlerFactory;
import interfaces.IServerMessageHandler;

public class ServerHandlerFactory implements IServerHandlerFactory {

    public IServerMessageHandler getHandler(String classname, Object game)
    {
        IGameLogic igame = (IGameLogic) game;
        switch (classname){
            case "PlayerTestMessage":
                return new TestHandler(igame);
            default:
                return null;
        }
    }
}

package messaging;

import interfaces.IGameLogic;
import interfaces.IServerHandlerFactory;
import interfaces.IServerMessageHandler;

public class ServerHandlerFactory implements IServerHandlerFactory {

    public IServerMessageHandler getHandler(String classname, Object game)
    {
        IGameLogic igame = (IGameLogic) game;
        switch (classname){
            case "PlayerLoginMessage":
                return new LoginHandler(igame);
            default:
                return null;
        }
    }
}

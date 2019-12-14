package messaging;

import interfaces.IGame;
import interfaces.IServerHandlerFactory;
import interfaces.IServerMessageHandler;

public class ServerHandlerFactory implements IServerHandlerFactory {

    public IServerMessageHandler getHandler(String classname, Object game)
    {
        IGame igame = (IGame) game;
        switch (classname){
            case "PlayerTestMessage":
                return new TestHandler(igame);
            default:
                return null;
        }
    }
}

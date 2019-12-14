package messaging;

import interfaces.IClientHandlerFactory;
import interfaces.IClientMessageHandler;

public class ClientHandlerFactory implements IClientHandlerFactory {

    public IClientMessageHandler getHandler(String classname, Object game){

        switch (classname){
            case "PlayerTestMessage":
                return new TestResultHandler();
            default:
                return null;
        }
    }
}

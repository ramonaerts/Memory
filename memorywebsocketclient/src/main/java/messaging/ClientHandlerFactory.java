package messaging;

public class ClientHandlerFactory {

    public IClientMessageHandler getHandler(String classname){

        switch (classname){
            case "PlayerTestMessage":
                return new TestResultHandler();
            default:
                return null;
        }
    }
}

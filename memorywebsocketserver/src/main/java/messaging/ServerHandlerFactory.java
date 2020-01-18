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
            case PLAYERJOINGAME:
                return new JoinGameHandler(igame);
            case TURNCARD:
                return new TurnCardHandler(igame);
            case PLAYERREGISTER:
                return new RegisterHandler(igame);
            case LOGOUT:
                return new LogOutHandler(igame);
            case PLAYERLEAVESGAME:
                return new LeaveGameHandler(igame);
            default:
                return null;
        }
    }
}

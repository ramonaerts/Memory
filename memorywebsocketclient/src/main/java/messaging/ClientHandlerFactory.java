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
            case PLAYERREGISTERRESULT:
                return new RegisterResultHandler(igameClient);
            case UPDATELOBBY:
                return new UpdateLobbyResultHandler(igameClient);
            case STARTGAMERESULT:
                return new StartGameResultHandler(igameClient);
            case JOINGAMERESULT:
                return new JoinGameResultHandler(igameClient);
            case PLAYERJOINSGAME:
                return new PlayerJoinsHandler(igameClient);
            case FEEDBACK:
                return new FeedbackMessageHandler(igameClient);
            case SENDCARDINFO:
                return new CardInfoHandler(igameClient);
            case TURNCARDBACK:
                return new TurnCardBackHandler(igameClient);
            case PLAYERPOINT:
                return new PlayerPointHandler(igameClient);
            case GAMERESULT:
                return new GameResultHandler(igameClient);
            default:
                return null;
        }
    }
}

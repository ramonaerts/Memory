package messaging;

import interfaces.IGameLogic;
import messages.LogOutMessage;

public class LogOutHandler extends BaseServerMessageHandler<LogOutMessage> {

    private IGameLogic game;

    public LogOutHandler(IGameLogic game) {this.game = game;}

    @Override
    public void handleMessageInternal(LogOutMessage message, String sessionId) {
        game.logOutPlayer(sessionId);
    }

}

package messaging;

import interfaces.IGameLogic;
import messages.LeaveGameMessage;

public class LeaveGameHandler extends BaseServerMessageHandler<LeaveGameMessage> {

    private IGameLogic game;

    public LeaveGameHandler(IGameLogic game) {
        this.game = game;
    }

    @Override
    public void handleMessageInternal(LeaveGameMessage message, String sessionId) {
        game.leaveGame(message.getGameId(), sessionId);
    }
}


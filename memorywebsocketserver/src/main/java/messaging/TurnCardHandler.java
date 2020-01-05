package messaging;

import interfaces.IGameLogic;
import messages.TurnCardMessage;

public class TurnCardHandler extends BaseServerMessageHandler<TurnCardMessage> {

    private IGameLogic game;

    public TurnCardHandler(IGameLogic game) {this.game = game;}

    @Override
    public void handleMessageInternal(TurnCardMessage message, String sessionId) {
        game.turnCard(message.getY(), message.getX(), message.getGameId(), sessionId);
    }
}

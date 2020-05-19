package messaging;

import interfaces.IGameLogic;
import messages.JoinGameMessage;

public class JoinGameHandler extends BaseServerMessageHandler<JoinGameMessage> {

    private IGameLogic game;

    public JoinGameHandler(IGameLogic game) {this.game = game;}

    @Override
    public void handleMessageInternal(JoinGameMessage message, String sessionId) {
        game.joinGame(sessionId);
    }
}

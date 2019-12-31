package messaging;

import interfaces.IGameLogic;
import messages.StartGameMessage;

public class StartGameHandler extends BaseServerMessageHandler<StartGameMessage> {

    private IGameLogic game;

    public StartGameHandler(IGameLogic game) {this.game = game;}

    @Override
    public void handleMessageInternal(StartGameMessage message, String sessionId) {
        game.startGame(sessionId);
    }
}

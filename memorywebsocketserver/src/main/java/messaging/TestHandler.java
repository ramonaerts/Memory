package messaging;

import interfaces.IGameLogic;
import messages.*;

public class TestHandler extends BaseServerMessageHandler<PlayerTestMessage> {

    private IGameLogic game;

    public TestHandler(IGameLogic game) {this.game = game;}

    @Override
    public void handleMessageInternal(PlayerTestMessage message, String sessionId) {
        game.registerPlayer(message.getUsername(), message.getPassword(), sessionId);
    }
}

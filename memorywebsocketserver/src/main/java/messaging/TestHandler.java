package messaging;

import interfaces.IGame;
import messages.*;

public class TestHandler extends BaseServerMessageHandler<PlayerTestMessage> {

    private IGame game;

    public TestHandler(IGame game) {this.game = game;}

    @Override
    public void handleMessageInternal(PlayerTestMessage message, String sessionId) {
        game.registerPlayer(message.getUsername(), message.getPassword(), sessionId);
    }
}

package messaging;

import interfaces.IGameLogic;
import messages.PlayerLoginMessage;

public class RegisterHandler extends BaseServerMessageHandler<PlayerLoginMessage> {

    private IGameLogic game;

    public RegisterHandler(IGameLogic game) {this.game = game;}

    @Override
    public void handleMessageInternal(PlayerLoginMessage message, String sessionId) {
        game.registerPlayer(message.getUsername(), message.getPassword(), sessionId);
    }
}

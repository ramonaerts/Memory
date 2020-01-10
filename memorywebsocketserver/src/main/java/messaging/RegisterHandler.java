package messaging;

import interfaces.IGameLogic;
import messages.PlayerAuthenticationMessage;

public class RegisterHandler extends BaseServerMessageHandler<PlayerAuthenticationMessage> {

    private IGameLogic game;

    public RegisterHandler(IGameLogic game) {this.game = game;}

    @Override
    public void handleMessageInternal(PlayerAuthenticationMessage message, String sessionId) {
        game.registerPlayer(message.getUsername(), message.getPassword(), sessionId);
    }
}

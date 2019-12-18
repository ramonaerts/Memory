package messaging;

import interfaces.IGameLogic;
import messages.*;

public class LoginHandler extends BaseServerMessageHandler<PlayerLoginMessage> {

    private IGameLogic game;

    public LoginHandler(IGameLogic game) {this.game = game;}

    @Override
    public void handleMessageInternal(PlayerLoginMessage message, String sessionId) {
        game.loginPlayer(message.getUsername(), message.getPassword(), sessionId);
    }
}

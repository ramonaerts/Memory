package messaging;

import interfaces.IGameClient;
import messages.*;

public class LoginResultHandler extends BaseClientMessageHandler<PlayerAuthenticationResultMessage> {

    private IGameClient client;

    public LoginResultHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(PlayerAuthenticationResultMessage message, String sessionId) {
        client.loginResult(message.getResult(), message.getPlayer());
    }
}

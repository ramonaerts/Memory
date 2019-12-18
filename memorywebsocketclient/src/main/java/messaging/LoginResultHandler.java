package messaging;

import interfaces.IGameClient;
import messages.*;

public class LoginResultHandler extends BaseClientMessageHandler<PlayerLoginResultMessage> {

    private IGameClient client;

    public LoginResultHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(PlayerLoginResultMessage message, String sessionId) {
        client.loginResult(message.getResult());
    }
}

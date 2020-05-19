package messaging;

import interfaces.IGameClient;
import messages.PlayerAuthenticationResultMessage;

public class RegisterResultHandler extends BaseClientMessageHandler<PlayerAuthenticationResultMessage> {

    private IGameClient client;

    public RegisterResultHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(PlayerAuthenticationResultMessage message, String sessionId) {
        client.registerResult(message.getResult(), message.getPlayer());
    }
}

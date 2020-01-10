package messaging;

import interfaces.IGameClient;
import messages.PlayerLoginResultMessage;

public class RegisterResultHandler extends BaseClientMessageHandler<PlayerLoginResultMessage> {

    private IGameClient client;

    public RegisterResultHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(PlayerLoginResultMessage message, String sessionId) {
        client.registerResult(message.getResult(), message.getPlayer());
    }
}

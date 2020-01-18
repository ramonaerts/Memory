package messaging;

import interfaces.IGameClient;
import messages.PlayerJoinsMessage;

public class PlayerJoinsHandler extends BaseClientMessageHandler<PlayerJoinsMessage> {

    private IGameClient client;

    public PlayerJoinsHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(PlayerJoinsMessage message, String sessionId) {
        client.playerJoinsGame(message.getOpponent());
    }
}

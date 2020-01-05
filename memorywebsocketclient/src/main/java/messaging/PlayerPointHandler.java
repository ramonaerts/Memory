package messaging;

import interfaces.IGameClient;
import messages.SendPointMessage;

public class PlayerPointHandler extends BaseClientMessageHandler<SendPointMessage> {

    private IGameClient client;

    public PlayerPointHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(SendPointMessage message, String sessionId) {
        client.sendPoint(message.getPlayerNr());
    }
}

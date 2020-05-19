package messaging;

import interfaces.IGameClient;
import messages.TurnCardBackMessage;

public class TurnCardBackHandler extends BaseClientMessageHandler<TurnCardBackMessage> {

    private IGameClient client;

    public TurnCardBackHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(TurnCardBackMessage message, String sessionId) {
        client.turnCardBack(message.getCoordinate());
    }
}

package messaging;

import interfaces.IGameClient;
import messages.CardInfoMessage;

public class CardInfoHandler extends BaseClientMessageHandler<CardInfoMessage> {

    private IGameClient client;

    public CardInfoHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(CardInfoMessage message, String sessionId) {
        client.showCardInfo(message.getCardValue(), message.getCoordinate(), message.getPlayerNr());
    }
}

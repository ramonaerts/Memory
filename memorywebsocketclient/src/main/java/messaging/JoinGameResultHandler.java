package messaging;

import interfaces.IGameClient;
import messages.JoinGameResultMessage;

public class JoinGameResultHandler extends BaseClientMessageHandler<JoinGameResultMessage> {

    private IGameClient client;

    public JoinGameResultHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(JoinGameResultMessage message, String sessionId) {
        client.joinGameResult(message.getResult(), message.getOpponent());
    }
}

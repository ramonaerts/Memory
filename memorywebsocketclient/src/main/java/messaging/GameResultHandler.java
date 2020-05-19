package messaging;

import interfaces.IGameClient;
import messages.GameResultMessage;

public class GameResultHandler extends BaseClientMessageHandler<GameResultMessage> {

    private IGameClient client;

    public GameResultHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(GameResultMessage message, String sessionId) {
        client.gameResult(message.getResult());
    }
}

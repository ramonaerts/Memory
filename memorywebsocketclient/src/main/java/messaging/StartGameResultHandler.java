package messaging;

import interfaces.IGameClient;
import messages.StartGameResultMessage;

public class StartGameResultHandler extends BaseClientMessageHandler<StartGameResultMessage> {

    private IGameClient client;

    public StartGameResultHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(StartGameResultMessage message, String sessionId) {
        client.startGameResult(message.getResult());
    }
}

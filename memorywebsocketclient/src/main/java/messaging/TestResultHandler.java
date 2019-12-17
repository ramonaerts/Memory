package messaging;

import interfaces.IGameClient;
import messages.*;

public class TestResultHandler extends BaseClientMessageHandler<PlayerTestResultMessage> {

    private IGameClient client;

    public TestResultHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(PlayerTestResultMessage message, String sessionId) {
        client.receivePlayer(message.getUsername());
    }
}

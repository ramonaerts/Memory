package messaging;

import interfaces.IGameClient;
import messages.GameFeedbackMessage;

public class FeedbackMessageHandler extends BaseClientMessageHandler<GameFeedbackMessage> {

    private IGameClient client;

    public FeedbackMessageHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(GameFeedbackMessage message, String sessionId) {
        client.feedback(message.getMessage());
    }
}

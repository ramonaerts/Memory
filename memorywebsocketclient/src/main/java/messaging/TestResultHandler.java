package messaging;

import messages.*;

public class TestResultHandler extends BaseClientMessageHandler<PlayerTestResultMessage> {

    //private IGameClient client = new GameClient();

    @Override
    public void handleMessageInternal(PlayerTestResultMessage message, String sessionId) {
        //client.receivePlayer(message.getUsername());
    }
}

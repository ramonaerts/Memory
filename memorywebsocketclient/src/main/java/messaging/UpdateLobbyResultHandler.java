package messaging;

import interfaces.IGameClient;
import messages.UpdateLobbyPlayersMessage;

public class UpdateLobbyResultHandler extends BaseClientMessageHandler<UpdateLobbyPlayersMessage> {

    private IGameClient client;

    public UpdateLobbyResultHandler(IGameClient client)
    {
        this.client = client;
    }

    @Override
    public void handleMessageInternal(UpdateLobbyPlayersMessage message, String sessionId) {
        client.updateLobby(message.getPlayerName());
    }
}

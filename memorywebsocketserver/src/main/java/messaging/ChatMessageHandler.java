package messaging;

import interfaces.IGameLogic;
import messages.ChatMessage;

public class ChatMessageHandler extends BaseServerMessageHandler<ChatMessage> {

    private IGameLogic game;

    public ChatMessageHandler(IGameLogic game) {
        this.game = game;
    }

    @Override
    public void handleMessageInternal(ChatMessage message, String sessionId) {
        game.chatMessage(message.getMessage(), message.getGameId(), sessionId);
    }
}

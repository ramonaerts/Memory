package server;

import interfaces.*;
import messages.*;

import java.util.List;

public class ServerMessageGenerator implements IServerMessageGenerator {
    private IServerWebSocket server;

    public ServerMessageGenerator(IServerWebSocket server) {
        this.server = server;
    }

    public void sendPlayerResult(boolean loginResult, Object player, String sessionId){
        server.sendTo(sessionId, new PlayerLoginResultMessage(loginResult, player), MessageOperation.PLAYERLOGINRESULT);
    }

    public void updateLobbyList(List<String> players, String sessionId){
        server.sendTo(sessionId, new UpdateLobbyPlayersMessage(players), MessageOperation.UPDATELOBBY);
    }

    public void sendGameStartResult(boolean startResult, int gameId, String sessionId){
        server.sendTo(sessionId, new StartGameResultMessage(startResult, gameId), MessageOperation.STARTGAMERESULT);
    }

    public void sendGameJoinResult(boolean joinResult, int gameId, Object opponent, String sessionId){
        server.sendTo(sessionId, new JoinGameResultMessage(joinResult, gameId, opponent), MessageOperation.JOINGAMERESULT);
    }

    public void playerJoinsGame(Object opponent, String sessionId) {
        server.sendTo(sessionId, new PlayerJoinsMessage(opponent), MessageOperation.PLAYERJOINSGAME);
    }

    public void sendGameFeedback(String message, String sessionId) {
        server.sendTo(sessionId, new GameFeedbackMessage(message), MessageOperation.FEEDBACK);
    }

    public void sendCardInfo(int cardValue, Object coordinate, boolean firstPlayer, String sessionId) {
        server.sendTo(sessionId, new CardInfoMessage(cardValue, coordinate, firstPlayer), MessageOperation.SENDCARDINFO);
    }
}

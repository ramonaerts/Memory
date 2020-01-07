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

    public void turnCardBack(Object coordinate, String sessionId) {
        server.sendTo(sessionId, new TurnCardBackMessage(coordinate), MessageOperation.TURNCARDBACK);
    }

    public void sendPointMessage(int playerNr, String sessionId) {
        server.sendTo(sessionId, new SendPointMessage(playerNr), MessageOperation.PLAYERPOINT);
    }

    public void sendCardInfo(int cardValue, Object coordinate, int playerNr, String sessionId) {
        server.sendTo(sessionId, new CardInfoMessage(cardValue, coordinate, playerNr), MessageOperation.SENDCARDINFO);
    }

    public void sendGameResult(Object result, String sessionId) {
        server.sendTo(sessionId, new GameResultMessage(result), MessageOperation.GAMERESULT);
    }
}

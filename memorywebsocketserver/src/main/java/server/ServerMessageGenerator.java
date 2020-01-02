package server;

import interfaces.*;
import messages.*;

import java.util.List;

public class ServerMessageGenerator implements IServerMessageGenerator {
    private IServerWebSocket server;

    public ServerMessageGenerator(IServerWebSocket server) {
        this.server = server;
    }

    public void sendPlayerResult(boolean loginResult, String sessionId){
        server.sendTo(sessionId, new PlayerLoginResultMessage(loginResult), MessageOperation.PLAYERLOGINRESULT);
    }

    public void updateLobbyList(List<String> players, String sessionId){
        server.sendTo(sessionId, new UpdateLobbyPlayersMessage(players), MessageOperation.UPDATELOBBY);
    }

    public void sendGameStartResult(boolean startResult, String sessionId){
        server.sendTo(sessionId, new StartGameResultMessage(startResult), MessageOperation.STARTGAMERESULT);
    }

    public void sendGameJoinResult(boolean joinResult, String opponentName, String sessionId){
        server.sendTo(sessionId, new JoinGameResultMessage(joinResult, opponentName), MessageOperation.JOINGAMERESULT);
    }

    public void playerJoinsGame(String opponentName, String sessionId) {
        server.sendTo(sessionId, new PlayerJoinsMessage(opponentName), MessageOperation.PLAYERJOINSGAME);
    }
}

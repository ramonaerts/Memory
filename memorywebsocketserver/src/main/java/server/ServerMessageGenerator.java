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

    public void sendGameStartResult(boolean startResult, String sessionId){
        server.sendTo(sessionId, new StartGameResultMessage(startResult), MessageOperation.STARTGAMERESULT);
    }

    public void sendGameJoinResult(boolean joinResult, Object opponent, String sessionId){
        server.sendTo(sessionId, new JoinGameResultMessage(joinResult, opponent), MessageOperation.JOINGAMERESULT);
    }

    public void playerJoinsGame(Object opponent, String sessionId) {
        server.sendTo(sessionId, new PlayerJoinsMessage(opponent), MessageOperation.PLAYERJOINSGAME);
    }
}

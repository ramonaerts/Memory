package mocks;

import interfaces.IServerMessageGenerator;

import java.util.List;

public class MessageGeneratorMock implements IServerMessageGenerator {
    @Override
    public void sendPlayerResult(boolean loginResult, Object player, String sessionId) {

    }

    @Override
    public void sendRegisterResult(boolean registerResult, Object player, String sessionId) {

    }

    @Override
    public void updateLobbyList(List<String> players, String sessionId) {

    }

    @Override
    public void sendGameStartResult(boolean startResult, int gameId, String sessionId) {

    }

    @Override
    public void sendGameJoinResult(boolean joinResult, int gameId, Object opponent, String sessionId) {

    }

    @Override
    public void playerJoinsGame(Object opponent, String sessionId) {

    }

    @Override
    public void sendCardInfo(int cardValue, Object coordinate, int playerNr, String sessionId) {

    }

    @Override
    public void turnCardBack(Object coordinate, String sessionId) {

    }

    @Override
    public void sendPointMessage(int playerNr, String sessionId) {

    }

    @Override
    public void sendGameFeedback(String message, String sessionId) {

    }

    @Override
    public void sendGameResult(Object result, String sessionId) {

    }
}

package mocks;

import interfaces.IRestClient;
import models.Player;

import java.util.ArrayList;
import java.util.List;

public class RestClientMock implements IRestClient {

    private Player mockPlayer = new Player("Ramon", "Aerts", "1");
    private List<Player> mockPlayers = new ArrayList<>();

    public RestClientMock() {
        mockPlayer.setWins(10);
        mockPlayer.setDraws(10);
        mockPlayer.setLosses(10);
        mockPlayer.setScore(100);
        Player secondPlayer = new Player("user", "password", "2");
        Player thirdPlayer = new Player("username", "badpassword", "3");
        mockPlayers.add(mockPlayer); mockPlayers.add(secondPlayer); mockPlayers.add(thirdPlayer);
    }

    @Override
    public boolean checkUsername(String username) {
        for (Player player : mockPlayers) {
            if (username.equals(player.getUsername())) return false;
        }
        return true;
    }

    @Override
    public Object getPlayerByCredentials(String username, String password) {
        if (username.equals(mockPlayer.getUsername()) && password.equals(mockPlayer.getPassword())) return mockPlayer;
        else return null;
    }

    @Override
    public Object registerPlayer(String username, String password) {
        if (checkUsername(username)){
            Player newPlayer = new Player(username, password, "4");
            return newPlayer;
        }
        else return null;
    }

    @Override
    public void updatePlayer(Object player) {

    }
}

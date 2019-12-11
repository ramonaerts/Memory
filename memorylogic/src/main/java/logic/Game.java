package logic;

import models.*;
import server.ServerMessageGenerator;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private int gameID;
    private List<Player> players = new ArrayList<>();
    private int playerAmount;
    private Card[][] cards = new Card[6][3];
    private int round;
    boolean singlePlayer;

    private ServerMessageGenerator generator = new ServerMessageGenerator();

    public Game() {

    }

    public void registerPlayer(String username, String password, String sessionId)
    {
        Player player = new Player(username, password, sessionId);

        generator.sendPlayer(player.getUsername());
    }
}
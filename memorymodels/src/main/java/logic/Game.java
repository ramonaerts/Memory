package logic;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int gameID;
    private List<Player> players = new ArrayList<>();
    private int playerAmount;
    private Card[][] cards = new Card[6][3];
    private int round;
    boolean singlePlayer;

}

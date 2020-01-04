package models;

import enums.CardState;
import interfaces.IServerMessageGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Card[][] cards = new Card[6][3];
    private List<Player> playersInGame = new ArrayList<>();
    private int playeramount = 0;
    private boolean gamestarted;

    private IServerMessageGenerator generator;

    public Game (IServerMessageGenerator generator) {
        this.generator = generator;
    }

    public int getPlayeramount() {
        return playeramount;
    }

    public List<Player> getPlayersInGame() {
        return playersInGame;
    }

    public Player getOpponent(String sessionId)
    {
        for (Player player : playersInGame)
        {
            if (!player.getSessionID().equals(sessionId)) return player;
        }
        return null;
    }

    public void playerStartsGame(Player player)
    {
        player.setPlayerID(1); //hardcoded, remove if login is implemented
        playersInGame.add(player);
        gamestarted = false;
        generateCards();
        String test = "hoit";
    }

    public void playerJoinsGame(Player player)
    {
        player.setPlayerID(2); //hardcoded, remove if login is implemented
        playersInGame.add(player);
        gamestarted = true;
    }

    public void playerTurnsCard(Player player, int xPos, int yPos)
    {
        if (gamestarted){
            player.setTurnAmount(+1);
            for (Card[] xCard : cards) {
                for (Card card : xCard) {

                    card.setCardState(CardState.TURNED);
                    card.setTurnedBy(player.getPlayerID());

                    if (checkIfTwoCardsTurned(player)) {
                        if(checkIfCardsMatch(card)){
                            //TODO: send message to client that player has guessed a card set.
                        }
                        else{
                            turnCardsBack(card);
                        }
                    }
                }
            }
            //TODO: else Send message with card information to users
        }
        //TODO: else Send message to user that the game has not started yet.

    }

    private boolean checkIfTwoCardsTurned(Player player) {
        return player.getTurnAmount() == 2;
    }

    private boolean checkIfCardsMatch(Card turnedCard) {
        for (Card[] xCard : cards) {
            for (Card card : xCard) {
                if (card.getTurnedBy() == 0) continue;
                if (card.getTurnedBy() == turnedCard.getTurnedBy() && card.getValue() == turnedCard.getValue())
                {
                    turnedCard.setCardState(CardState.GUESSED);
                    turnedCard.setTurnedBy(0);
                    card.setCardState(CardState.GUESSED);
                    card.setTurnedBy(0);
                    return true;
                }
            }
        }
        return false;
    }

    private void turnCardsBack(Card card){

    }

    private void generateCards()
    {
        List<Integer> num = new ArrayList<>();
        int id = 1;
        for (int i=0; i <= 1; i++)
        {
            for (int j=1; j <= 9; j++) num.add(j);
        }

        for(int x=0; x < cards.length; x++)
        {
            for(int y=0; y < cards[x].length; y++)
            {
                Card card = new Card(num.get(0), new Coordinate(x, y), id);
                cards[x][y] = card;
                id++;
                num.remove(0);
            }
        }
        shuffleCards();
    }

    private void shuffleCards()
    {
        Random random = new Random();

        for (int i = cards.length - 1; i > 0; i--) {
            for (int j = cards[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                Card tempcard = cards[i][j];
                cards[i][j] = cards[m][n];
                cards[m][n] = tempcard;
            }
        }
    }
}

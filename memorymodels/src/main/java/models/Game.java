package models;

import enums.CardState;
import interfaces.IServerMessageGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

public class Game {
    private List<Card> cards = new ArrayList<>();
    private List<Player> playersInGame = new ArrayList<>();
    private int playeramount = 0;
    private boolean gamestarted;

    private ExecutorService pool;

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
    }

    public void playerJoinsGame(Player player)
    {
        player.setPlayerID(2); //hardcoded, remove if login is implemented
        playersInGame.add(player);
        gamestarted = true;
    }

    public void playerTurnsCard(Player player, int xPos, int yPos)
    {
        if (gamestarted) {
            player.setTurnAmount(+1);
            for (Card card : cards) {
                if (card.getCoordinate().getX() == xPos && card.getCoordinate().getY() == yPos) {

                    card.setTurnedBy(player.getPlayerID());
                    card.setCardState(CardState.TURNED);

                    if (checkIfTwoCardsTurned(player)) checkIfCardsMatch(card);
                    else {
                        //TODO: else Send message with card information to users
                        return;
                    }
                }
            }
        }
        //TODO: else Send message to user that the game has not started yet.
    }

    private boolean checkIfTwoCardsTurned(Player player) {
        return player.getTurnAmount() == 2;
    }

    private void checkIfCardsMatch(Card turnedCard) {
        for (Card card : cards) {
            if (card.getTurnedBy() == 0) continue;
            if (card.getTurnedBy() == turnedCard.getTurnedBy() && card.getCardID() != turnedCard.getCardID()) {
                if(card.getValue() == turnedCard.getValue())
                {
                    turnedCard.setCardState(CardState.GUESSED);
                    turnedCard.setTurnedBy(0);
                    card.setCardState(CardState.GUESSED);
                    card.setTurnedBy(0);
                    //TODO: Send message with card information to users + gain point for user
                    return;
                }
                else turnCardsBack(card, turnedCard);
            }
        }
    }

    private void turnCardsBack(Card cardOne, Card cardTwo){

    }

    private void generateCards()
    {
        List<Card> unstortedCards = new ArrayList<>();
        int id = 1;

        for (int i=0; i <= 1; i++)
        {
            for (int j=1; j <= 9; j++)
            {
                Card card = new Card(j, id);
                unstortedCards.add(card);
                id++;
            }
        }
        shuffleCards(unstortedCards);
    }

    private void shuffleCards(List<Card> unsortedCards)
    {
        Collections.shuffle(unsortedCards);
        Random rand = new Random();

        for (int x=1; x < 4; x++) {
            for (int y=1; y < 7; y++) {
                int randomIndex = rand.nextInt(unsortedCards.size());
                Card randomCard = unsortedCards.get(randomIndex);
                randomCard.setCoordinate(new Coordinate(x, y));
                unsortedCards.remove(randomCard);
                cards.add(randomCard);
            }
        }
    }
}

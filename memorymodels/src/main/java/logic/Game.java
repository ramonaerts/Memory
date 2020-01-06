package logic;

import enums.CardState;
import interfaces.IServerMessageGenerator;
import models.Card;
import models.Coordinate;
import models.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

public class Game {
    private int gameId;
    private List<Card> cards = new ArrayList<>();
    private List<Player> playersInGame = new ArrayList<>();
    private boolean gamestarted;

    private ExecutorService pool;
    private Random rand = new Random();

    private IServerMessageGenerator generator;

    public Game (IServerMessageGenerator generator) {
        this.generator = generator;
    }

    public int getGameID() {
        return gameId;
    }

    public void setGameID(int gameId) {
        this.gameId = gameId;
    }

    public List<Player> getPlayersInGame() {
        return playersInGame;
    }

    private Player getPlayerBySession(String sessionId)
    {
        for (Player player : playersInGame)
        {
            if (player.getSessionID().equals(sessionId)) return player;
        }
        return null;
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
        String test = "test";
    }

    public void playerJoinsGame(Player player)
    {
        player.setPlayerID(2); //hardcoded, remove if login is implemented
        playersInGame.add(player);
        gamestarted = true;
    }

    public void playerTurnsCard(String sessionId, int xPos, int yPos)
    {
        Player player = getPlayerBySession(sessionId);
        if (gamestarted) {
            assert player != null;
            player.setTurnAmount(player.getTurnAmount() + 1);
            for (Card card : cards) {
                if (card.getCoordinate().getX() == xPos && card.getCoordinate().getY() == yPos) {
                    if (card.getCardState().equals(CardState.TURNED) || card.getCardState().equals(CardState.GUESSED)) {
                        sendMessageToPlayers("This card has already been turned, choose another one");
                        return;
                    }
                    card.setTurnedBy(player.getPlayerID());
                    card.setCardState(CardState.TURNED);

                    for (Player inGamePlayer : playersInGame) generator.sendCardInfo(card.getValue(), card.getCoordinate(), player.getInGameNr(), inGamePlayer.getSessionID());

                    if (checkIfTwoCardsTurned(player)) checkIfCardsMatch(card, player);
                    else return;
                }
            }
        }
        else generator.sendGameFeedback("The game will not start until a second player has joined", sessionId);
    }

    private void sendMessageToPlayers(String message){
        for (Player player : playersInGame) generator.sendGameFeedback(message, player.getSessionID());
    }

    private boolean checkIfTwoCardsTurned(Player player) {
        return player.getTurnAmount() == 2;
    }

    private void checkIfCardsMatch(Card turnedCard, Player player) {
        player.setTurnAmount(0);
        for (Card card : cards) {
            if (card.getTurnedBy() == 0) continue;
            if (card.getTurnedBy() == turnedCard.getTurnedBy() && card.getCardID() != turnedCard.getCardID()) {
                if(card.getValue() == turnedCard.getValue())
                {
                    turnedCard.setCardState(CardState.GUESSED);
                    turnedCard.setTurnedBy(0);
                    card.setCardState(CardState.GUESSED);
                    card.setTurnedBy(0);
                    player.setPoints(+1);
                    for (Player inGamePlayer : playersInGame) generator.sendPointMessage(player.getInGameNr(), inGamePlayer.getSessionID());
                    return;
                }
                else turnCardsBack(player);
            }
        }
    }

    private void turnCardsBack(Player player){
        for (Card card : cards)
        {
            if (card.getTurnedBy() == player.getPlayerID()){
                card.setCardState(CardState.HIDDEN);
                card.setTurnedBy(0);
                for (Player inGamePlayer : playersInGame) generator.turnCardBack(card.getCoordinate(), inGamePlayer.getSessionID());
            }
        }
    }

    private void generateCards()
    {
        List<Card> unsortedCards = new ArrayList<>();
        int id = 1;

        for (int i=0; i <= 1; i++)
        {
            for (int j=1; j <= 9; j++)
            {
                Card card = new Card(j, id);
                unsortedCards.add(card);
                id++;
            }
        }
        shuffleCards(unsortedCards);
    }

    private void shuffleCards(List<Card> unsortedCards)
    {
        Collections.shuffle(unsortedCards);

        for (int y=0; y < 3; y++) {
            for (int x=0; x < 6; x++) {

                int randomIndex = rand.nextInt(unsortedCards.size());
                Card randomCard = unsortedCards.get(randomIndex);
                randomCard.setCoordinate(new Coordinate(x, y));
                unsortedCards.remove(randomCard);
                cards.add(randomCard);
            }
        }
    }
}

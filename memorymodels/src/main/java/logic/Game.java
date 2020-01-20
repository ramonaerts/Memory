package logic;

import enums.CardState;
import enums.GameResult;
import enums.GameState;
import interfaces.IGameLogic;
import interfaces.IServerMessageGenerator;
import models.Card;
import models.Coordinate;
import models.Player;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    private int gameId;
    private List<Card> cards = new ArrayList<>();
    private List<Player> playersInGame = new ArrayList<>();
    private List<String> chatHistory = new ArrayList<>();
    private boolean gamestarted;
    private boolean gameEnded;

    private Random rand = new Random();

    private IServerMessageGenerator generator;
    private IGameLogic lobby;

    public Game (IServerMessageGenerator generator, IGameLogic lobby) {
        this.generator = generator;
        this.lobby = lobby;
        this.gameEnded = false;
    }

    public IServerMessageGenerator getGenerator() {
        return generator;
    }

    public int getGameID() {
        return gameId;
    }

    public void setGameID(int gameId) {
        this.gameId = gameId;
    }

    public boolean getGamestarted() {
        return gamestarted;
    }

    public void setGamestarted(boolean gamestarted) {
        this.gamestarted = gamestarted;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Player> getPlayersInGame() {
        return playersInGame;
    }

    public List<String> getChatHistory() {
        return chatHistory;
    }

    public Player getPlayerBySession(String sessionId)
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
        player.setAbleToPlay(true);
        player.setPoints(0);
        playersInGame.add(player);
        gamestarted = false;
        generateCards();
    }

    public void playerJoinsGame(Player player)
    {
        player.setAbleToPlay(true);
        player.setPoints(0);
        playersInGame.add(player);
        gamestarted = true;
    }

    public void playerLeavesGame(String sessionId){
        Player player = getPlayerBySession(sessionId);
        player.setGameState(GameState.LOBBY);

        if(!gameEnded){
            player.setGameResult(GameResult.LOSE);
            lobby.saveResults(sessionId);
        }

        playersInGame.remove(player);

        for (Player opponent : playersInGame) generator.sendGameFeedback(player.getUsername() + " has left the game, you can continue player of leave also", opponent.getSessionID());
    }

    public void playerTurnsCard(String sessionId, int xPos, int yPos)
    {
        Player player = getPlayerBySession(sessionId);
        assert player != null;
        if (gamestarted && player.getIsAbleToPlay()) {
            if (checkIfSpecificCardsTurned(2)){
                for (Player opponent : playersInGame) {
                    if(!opponent.getSessionID().equals(player.getSessionID())) opponent.setAbleToPlay(false);
                }
            }
            for (Card card : cards) {
                if (card.getCoordinate().getX() == xPos && card.getCoordinate().getY() == yPos) {
                    handleCards(card, player);
                }
            }
        }
        else generator.sendGameFeedback("You cant turn a card right now", sessionId);
    }

    private void handleCards(Card card, Player player){
        if (card.getCardState().equals(CardState.TURNED) || card.getCardState().equals(CardState.GUESSED)) {
            sendMessageToPlayers("This card has already been turned, choose another one");
            return;
        }
        player.setTurnAmount(player.getTurnAmount() + 1);
        card.setTurnedBy(player.getPlayerID());
        card.setCardState(CardState.TURNED);

        for (Player inGamePlayer : playersInGame) generator.sendCardInfo(card.getValue(), card.getCoordinate(), player.getInGameNr(), inGamePlayer.getSessionID());

        if (checkIfTwoCardsTurned(player)) checkIfCardsMatch(card, player);
    }

    private boolean checkIfTwoCardsTurned(Player player) {
        return player.getTurnAmount() == 2;
    }

    public void checkIfCardsMatch(Card turnedCard, Player player) {
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
                    player.setPoints(player.getPoints()+1);
                    for (Player inGamePlayer : playersInGame) generator.sendPointMessage(player.getInGameNr(), inGamePlayer.getSessionID());
                    checkForEndGame(player);
                    return;
                }
                else turnCardsBack(player);
            }
        }
    }

    private boolean checkIfSpecificCardsTurned(int cardsLeft){
        int cardAmount = 0;

        for (Card card : cards) {
            if(card.getCardState() == CardState.GUESSED) cardAmount++;
        }

        return cardAmount == cards.size() - cardsLeft;
    }

    private List<Card> getWrongCards(Player player){
        List<Card> wrongCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getTurnedBy() == player.getPlayerID()) wrongCards.add(card);
        }
        return wrongCards;
    }

    private void turnCardsBack(Player player){
        ExecutorService pool = Executors.newCachedThreadPool();
        for (Card card : getWrongCards(player))
        {
            for (Player inGamePlayer : getPlayersInGame()) {
                CardTurner cardTurner = new CardTurner(this, card, inGamePlayer);
                pool.execute(cardTurner);
            }
        }
        pool.shutdown();
    }

    public void checkForEndGame(Player player){
        if (!checkIfSpecificCardsTurned(0)) return;
        gameEnded = true;
        if (!checkForDraw(player)){
            Player winner = Collections.max(playersInGame, Comparator.comparing(Player::getPoints));
            for (Player memoryPlayer : playersInGame) {
                if(memoryPlayer.getPlayerID() == winner.getPlayerID()) memoryPlayer.setGameResult(GameResult.WIN);
                else memoryPlayer.setGameResult(GameResult.LOSE);
                lobby.saveResults(memoryPlayer.getSessionID());
            }
        }
        for (Player inGamePlayer : playersInGame) generator.sendGameResult(inGamePlayer.getGameResult(), inGamePlayer.getSessionID());
    }

    public boolean checkForDraw(Player player){
        int counter = 0;
        if(playersInGame.size() == 1) return false;
        for (Player opponent : playersInGame) {
            if(player.getPoints() == opponent.getPoints()){
                counter++;
                if(counter == playersInGame.size()){
                    player.setGameResult(GameResult.DRAW);
                    lobby.saveResults(player.getSessionID());
                }
            }
            else return false;
        }
        return true;
    }

    private void sendMessageToPlayers(String message){
        for (Player player : playersInGame) generator.sendGameFeedback(message, player.getSessionID());
    }

    public void receiveChatMessage(String message, String sessionId){
        Player player = getPlayerBySession(sessionId);
        String messageToPlayer = player.getUsername() + ": " + message;
        chatHistory.add(messageToPlayer);
        sendMessageToPlayers(messageToPlayer);
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

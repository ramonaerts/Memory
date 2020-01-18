package memoryunittest;

import enums.CardState;
import enums.GameResult;
import enums.GameState;
import interfaces.IRestClient;
import interfaces.IServerMessageGenerator;
import logic.Game;
import logic.MemoryLogic;
import mocks.MessageGeneratorMock;
import mocks.RestClientMock;
import models.Card;
import models.Coordinate;
import models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryTests {

    private MemoryLogic memorylogic;
    private Game game;
    private IServerMessageGenerator mockGenerator;

    @BeforeEach
    void setUp(){
        mockGenerator = new MessageGeneratorMock();
        IRestClient mockRestClient = new RestClientMock();
        memorylogic = new MemoryLogic(mockGenerator, mockRestClient);
        game = new Game(mockGenerator, memorylogic);
    }

    @Test
    void loginPlayerCorrectValues(){
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        assertEquals(1, memorylogic.getOnlinePlayers().size());
    }

    @Test
    void loginTwoPlayersCorrectValues(){
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        memorylogic.loginPlayer("user", "password", "2");
        assertEquals(2, memorylogic.getOnlinePlayers().size());
    }

    @Test
    void LoginOneWrongOneCorrect(){
        memorylogic.loginPlayer("user", "wrongpassword", "2");
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        assertEquals(1, memorylogic.getOnlinePlayers().size());
    }

    @Test
    void loginPlayerWrongPassword(){
        memorylogic.loginPlayer("Ramon", "wrongpassword", "1");
        assertEquals(0, memorylogic.getOnlinePlayers().size());
    }

    @Test
    void loginPlayerWrongUsername(){
        memorylogic.loginPlayer("wrongusername", "Aerts", "1");
        assertEquals(0, memorylogic.getOnlinePlayers().size());
    }

    @Test
    void loginPlayerWrongUsernameAndPassword(){
        memorylogic.loginPlayer("wrongusername", "wrongpassword", "1");
        assertEquals(0, memorylogic.getOnlinePlayers().size());
    }

    @Test
    void usernameIsAvailable(){
        assertTrue(memorylogic.checkIfUsernameExists("Harry"));
    }

    @Test
    void usernameIsNotAvailable() {
        assertFalse(memorylogic.checkIfUsernameExists("Ramon"));
    }

    @Test
    void usernameIsNotAvailable2() {
        assertFalse(memorylogic.checkIfUsernameExists("user"));
    }

    @Test
    void registerUserSuccess(){
        memorylogic.registerPlayer("newuser", "newpassword", "1");
        assertEquals(1, memorylogic.getOnlinePlayers().size());
    }

    @Test
    void registerUserUsernameExists(){
        memorylogic.registerPlayer("Ramon", "newpassword", "1");
        assertEquals(0, memorylogic.getOnlinePlayers().size());
    }

    @Test
    void gameIdTest(){
        Game gameOne = new Game(mockGenerator, memorylogic);
        Game gameTwo = new Game(mockGenerator, memorylogic);
        Game gameThree = new Game(mockGenerator, memorylogic);
        memorylogic.getActiveGames().add(gameOne); memorylogic.getActiveGames().add(gameTwo); memorylogic.getActiveGames().add(gameThree);
        assertEquals(4, memorylogic.createGameID());
    }

    @Test
    void getGameTest(){
        Game game = new Game(mockGenerator, memorylogic);
        game.setGameID(1);

        memorylogic.getActiveGames().add(game);

        assertEquals(game, memorylogic.getGame(1));
    }

    @Test
    void getGameDoesNotExistTest(){
        Game game = new Game(mockGenerator, memorylogic);
        game.setGameID(1);

        memorylogic.getActiveGames().add(game);

        assertNull(memorylogic.getGame(4));
    }

    @Test
    void getPlayerBySessionIdTest(){
        Player expectedPlayer = new Player("Ramon", "Aerts", "1");
        memorylogic.loginPlayer(expectedPlayer.getUsername(), expectedPlayer.getPassword(), "1");

        Player actualPlayer = memorylogic.getPlayer("1");

        assertEquals(expectedPlayer.getUsername(), actualPlayer.getUsername());
        assertEquals(expectedPlayer.getPassword(), actualPlayer.getPassword());
        assertEquals(expectedPlayer.getSessionID(), actualPlayer.getSessionID());
    }

    @Test
    void getPlayerBySessionIdTestFailed(){
        Player expectedPlayer = new Player("Ramon", "Aerts", "1");
        memorylogic.loginPlayer(expectedPlayer.getUsername(), expectedPlayer.getPassword(), "1");

        Player actualPlayer = memorylogic.getPlayer("5");

        assertNull(actualPlayer);
    }

    @Test
    void startGameTest(){
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        memorylogic.startGame("1");

        assertEquals(1, memorylogic.getActiveGames().size());
        assertEquals(1, memorylogic.getGame(1).getPlayersInGame().size());
        assertFalse(memorylogic.getGame(1).getGamestarted());
        assertEquals(GameState.PLAYING, memorylogic.getPlayer("1").getGameState());
    }

    @Test
    void startGameFailedTest(){
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        memorylogic.startGame("4");

        assertEquals(0, memorylogic.getActiveGames().size());
        assertEquals(GameState.LOBBY, memorylogic.getPlayer("1").getGameState());
    }

    @Test
    void joinGameTest(){
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        memorylogic.startGame("1");
        memorylogic.loginPlayer("user", "password", "2");
        memorylogic.joinGame("2");

        assertEquals(1, memorylogic.getActiveGames().size());
        assertEquals(2, memorylogic.getGame(1).getPlayersInGame().size());
        assertTrue(memorylogic.getGame(1).getGamestarted());
        assertEquals(GameState.PLAYING, memorylogic.getPlayer("2").getGameState());
        assertEquals(18, memorylogic.getGame(1).getCards().size());
    }

    @Test
    void joinGameFailedTest(){
        memorylogic.loginPlayer("user", "password", "2");
        memorylogic.joinGame("2");

        assertEquals(0, memorylogic.getActiveGames().size());
        assertEquals(GameState.LOBBY, memorylogic.getPlayer("2").getGameState());
    }

    @Test
    void joinGamePlayerDoesntExistTest(){
        memorylogic.loginPlayer("user", "password", "2");
        memorylogic.joinGame("5");

        assertEquals(0, memorylogic.getActiveGames().size());
        assertEquals(GameState.LOBBY, memorylogic.getPlayer("2").getGameState());
    }

    @Test
    void saveResultAfterDrawTest(){
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        memorylogic.getPlayer("1").setGameResult(GameResult.DRAW);

        memorylogic.saveResults("1");

        assertEquals(10, memorylogic.getPlayer("1").getWins());
        assertEquals(11, memorylogic.getPlayer("1").getDraws());
        assertEquals(10, memorylogic.getPlayer("1").getLosses());
    }

    @Test
    void saveResultAfterLoseTest(){
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        memorylogic.getPlayer("1").setGameResult(GameResult.LOSE);

        memorylogic.saveResults("1");

        assertEquals(10, memorylogic.getPlayer("1").getWins());
        assertEquals(10, memorylogic.getPlayer("1").getDraws());
        assertEquals(11, memorylogic.getPlayer("1").getLosses());
    }

    @Test
    void saveResultAfterWinTest(){
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        memorylogic.getPlayer("1").setGameResult(GameResult.WIN);

        memorylogic.saveResults("1");

        assertEquals(11, memorylogic.getPlayer("1").getWins());
        assertEquals(10, memorylogic.getPlayer("1").getDraws());
        assertEquals(10, memorylogic.getPlayer("1").getLosses());
    }

    @Test
    void turnCardCompleteTest(){
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        memorylogic.startGame("1");
        memorylogic.loginPlayer("user", "password", "2");
        memorylogic.joinGame("2");

        memorylogic.turnCard(1,1,1,"1");

        for (Card card : memorylogic.getGame(1).getCards()) {
            if (card.getCoordinate().getX() == 1 && card.getCoordinate().getY() == 1){
                assertEquals(CardState.TURNED, card.getCardState());
            }
        }
    }

    @Test
    void turnCardsOnAllSpacesCompleteTest(){
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        memorylogic.startGame("1");
        memorylogic.loginPlayer("user", "password", "2");
        memorylogic.joinGame("2");

        for (int y=0; y < 3; y++) {
            for (int x=0; x < 6; x++) {
                memorylogic.turnCard(x,y,1,"1");

                for (Card card : memorylogic.getGame(1).getCards()) {
                    if (card.getCoordinate().getX() == x && card.getCoordinate().getY() == y){
                        assertEquals(CardState.TURNED, card.getCardState());
                    }
                }
            }
        }
    }

    @BeforeEach
    void setUpGame(){
        Player playerOne = new Player("Ramon", "Aerts", "1");
        playerOne.setAbleToPlay(true);
        playerOne.setGameState(GameState.PLAYING);
        playerOne.setInGameNr(1);
        Player playerTwo = new Player("user", "password", "2");
        playerTwo.setAbleToPlay(true);
        playerOne.setInGameNr(2);
        playerTwo.setGameState(GameState.PLAYING);
        game.getPlayersInGame().add(playerOne); game.getPlayersInGame().add(playerTwo);
        setMockCards();
    }

    private void setMockCards(){
        int id = 1;

        List<Card> emptyCards = new ArrayList<>();
        for (int i=0; i <= 1; i++) {
            for (int j=1; j <= 9; j++) {
                Card card = new Card(j, id);
                emptyCards.add(card);
                id++;
            }
        }

        for (int y=0; y < 3; y++) {
            for (int x=0; x < 6; x++) {
                Card card = emptyCards.get(0);
                card.setCoordinate(new Coordinate(x, y));
                emptyCards.remove(card);
                game.getCards().add(card);
            }
        }
    }

    @Test
    void getOpponentTest(){
        Player actualPlayer = game.getOpponent("1");

        assertEquals("user", actualPlayer.getUsername());
        assertEquals("password", actualPlayer.getPassword());
        assertEquals("2", actualPlayer.getSessionID());
    }

    @Test
    void getPlayerInGameTest(){
        Player actualPlayer = game.getPlayerBySession("1");

        assertEquals("Ramon", actualPlayer.getUsername());
        assertEquals("Aerts", actualPlayer.getPassword());
        assertEquals("1", actualPlayer.getSessionID());
    }

    @Test
    void getPlayerInGameSecondTest(){
        Player actualPlayer = game.getPlayerBySession("2");

        assertEquals("user", actualPlayer.getUsername());
        assertEquals("password", actualPlayer.getPassword());
        assertEquals("2", actualPlayer.getSessionID());
    }

    @Test
    void getPlayerInGameFailedTest(){
        Player emptyPlayer = game.getPlayerBySession("3");

        assertNull(emptyPlayer);
    }

    @Test
    void playerTurnsCardTest(){
        game.setGamestarted(true);

        game.playerTurnsCard("1", 0,0);

        for (Card card : game.getCards()) {
            if(card.getCoordinate().getX() == 0 && card.getCoordinate().getY() == 0) assertEquals(CardState.TURNED, card.getCardState());
        }
        assertEquals(1, game.getPlayerBySession("1").getTurnAmount());
    }

    @Test
    void playerTurnsCardWhileGameNotStartedTest(){
        game.playerTurnsCard("1", 0,0);

        for (Card card : game.getCards()) {
            if(card.getCoordinate().getX() == 0 && card.getCoordinate().getY() == 0) assertEquals(CardState.HIDDEN, card.getCardState());
        }
        assertEquals(0, game.getPlayerBySession("1").getTurnAmount());
    }

    @Test
    void playerTurnsCardWhichIsTurnedTest(){
        game.setGamestarted(true);

        for (Card card : game.getCards()) {
            if(card.getCoordinate().getX() == 0 && card.getCoordinate().getY() == 0) card.setCardState(CardState.TURNED);
        }

        game.playerTurnsCard("1", 0,0);

        assertEquals(0, game.getPlayerBySession("1").getTurnAmount());
    }

    @Test
    void cardsMatchTest(){
        game.setGamestarted(true);

        Card turnedCard = null;
        for (Card card : game.getCards()) {
            if(card.getCoordinate().getX() == 0 && card.getCoordinate().getY() == 0){
                card.setCardState(CardState.TURNED);
                card.setTurnedBy(1);
            }
            if(card.getCoordinate().getX() == 3 && card.getCoordinate().getY() == 1) {
                card.setTurnedBy(1);
                turnedCard = card;
            }

        }

        game.checkIfCardsMatch(turnedCard, game.getPlayerBySession("1"));

        assertEquals(1, game.getPlayerBySession("1").getPoints());
    }

    @Test
    void cardsDontMatchTest(){
        game.setGamestarted(true);

        Card turnedCard = null;
        for (Card card : game.getCards()) {
            if(card.getCoordinate().getX() == 0 && card.getCoordinate().getY() == 0){
                card.setCardState(CardState.TURNED);
                card.setTurnedBy(1);
            }
            if(card.getCoordinate().getX() == 4 && card.getCoordinate().getY() == 1) {
                card.setTurnedBy(1);
                turnedCard = card;
            }

        }

        game.checkIfCardsMatch(turnedCard, game.getPlayerBySession("1"));

        assertEquals(0, game.getPlayerBySession("1").getPoints());
    }

    @Test
    void checkForDrawTest(){
        for (Player player : game.getPlayersInGame()) player.setPoints(4);

        game.checkForDraw();

        assertEquals(GameResult.DRAW, game.getPlayerBySession("1").getGameResult());
    }

    @Test
    void checkForDrawFalseTest(){
        game.getPlayerBySession("1").setPoints(5);

        game.checkForDraw();

        assertNotEquals(GameResult.DRAW, game.getPlayerBySession("1").getGameResult());
    }

    @Test
    void checkForWinTest(){
        game.getPlayerBySession("1").setPoints(5);
        game.getPlayerBySession("2").setPoints(4);

        for (Card card : game.getCards()) {
            card.setCardState(CardState.GUESSED);
        }

        game.checkForEndGame();

        assertEquals(GameResult.WIN, game.getPlayerBySession("1").getGameResult());
    }

    @Test
    void checkForLoseTest(){
        game.getPlayerBySession("1").setPoints(5);
        game.getPlayerBySession("2").setPoints(4);
        game.getPlayerBySession("1").setPlayerID(1);
        game.getPlayerBySession("2").setPlayerID(2);

        for (Card card : game.getCards()) {
            card.setCardState(CardState.GUESSED);
        }

        game.checkForEndGame();

        assertEquals(GameResult.LOSE, game.getPlayerBySession("2").getGameResult());
    }
}

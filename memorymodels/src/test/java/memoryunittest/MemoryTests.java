package memoryunittest;

import enums.GameState;
import interfaces.IGameLogic;
import interfaces.IRestClient;
import interfaces.IServerMessageGenerator;
import logic.Game;
import logic.MemoryLogic;
import mocks.MessageGeneratorMock;
import mocks.RestClientMock;
import models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryTests {

    private MemoryLogic memorylogic;
    private IServerMessageGenerator mockGenerator;

    @BeforeEach
    void setUp(){
        mockGenerator = new MessageGeneratorMock();
        IRestClient mockRestClient = new RestClientMock();
        memorylogic = new MemoryLogic(mockGenerator, mockRestClient);
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
    }
}

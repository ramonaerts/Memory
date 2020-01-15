package memoryunittest;

import interfaces.IGameLogic;
import interfaces.IRestClient;
import interfaces.IServerMessageGenerator;
import logic.MemoryLogic;
import mocks.MessageGeneratorMock;
import mocks.RestClientMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemoryTests {

    private MemoryLogic memorylogic;

    @BeforeEach
    void setUp(){
        IServerMessageGenerator mockGenerator = new MessageGeneratorMock();
        IRestClient mockRestClient = new RestClientMock();
        memorylogic = new MemoryLogic(mockGenerator, mockRestClient);
    }

    @Test
    void loginPlayerCorrectValues(){
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
}

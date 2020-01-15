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
    private IServerMessageGenerator mockgenerator;
    private IRestClient mockrestclient;

    @BeforeEach
    void setUp(){
        mockgenerator = new MessageGeneratorMock();
        mockrestclient = new RestClientMock();
        memorylogic = new MemoryLogic(mockgenerator, mockrestclient);
    }

    @Test
    void loginPlayerTest(){
        memorylogic.loginPlayer("Ramon", "Aerts", "1");
        assertEquals(1, memorylogic.onlinePlayers.size());
    }
}

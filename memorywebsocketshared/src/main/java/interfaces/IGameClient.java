package interfaces;

public interface IGameClient {
    void registerController(IController controller);

    void sendPlayer(String username, String password);

    void receivePlayer(String username);
}

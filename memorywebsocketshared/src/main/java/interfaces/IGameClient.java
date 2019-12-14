package interfaces;

public interface IGameClient {
    void sendPlayer(String username, String password);

    void receivePlayer(String username);
}

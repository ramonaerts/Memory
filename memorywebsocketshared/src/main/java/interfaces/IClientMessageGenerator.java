package interfaces;

public interface IClientMessageGenerator {
    void loginPlayer(String username, String password);
    void startGame();
    void joinGame();
}

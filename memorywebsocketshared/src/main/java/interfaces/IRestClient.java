package interfaces;

public interface IRestClient {
    boolean checkUsername(String username);
    Object getPlayerByCredentials(String username, String password);
    Object registerPlayer(String username, String password);
}

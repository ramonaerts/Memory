package interfaces;

public interface IRestController {
    void addUser(String username, String password);
    boolean getCheckUser(String username);
    Object getUserByCredentials(String username, String password);
}

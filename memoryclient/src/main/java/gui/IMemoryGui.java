package gui;

import java.util.List;

public interface IMemoryGui {
    //void registerPlayer(String username, String password);
    void loginResult(boolean loginresult);
    void updateLobby(List<String> players);
    void startGameResult(boolean startResult);
}

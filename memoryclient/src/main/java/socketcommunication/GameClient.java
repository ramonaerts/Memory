package socketcommunication;


import client.ClientMessageGenerator;

public class GameClient implements IGameClient {

    private ClientMessageGenerator messageGenerator = new ClientMessageGenerator();

    @Override
    public void sendPlayer(String username, String password) {
        messageGenerator.sendPlayer(username, password);
    }

    @Override
    public void receivePlayer(String username) {

    }
}

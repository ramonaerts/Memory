package models;

public class Game {
    private Card[][] cards = new Card[6][3];
    private Player player1;
    private Player player2;

    public void playerStartsGame(Player player)
    {
        this.player1 = player;
        generateCards();
    }

    public void playerJoinsGame(Player player)
    {
        this.player2 = player;
    }

    private void generateCards()
    {

    }
}

package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Card[][] cards = new Card[6][3];
    private Player player1;
    private Player player2;
    private int playeramount = 0;

    public int getPlayeramount() {
        return playeramount;
    }

    public void playerStartsGame(Player player)
    {
        this.player1 = player;
        playeramount++;
        generateCards();
    }

    public void playerJoinsGame(Player player)
    {
        this.player2 = player;
        playeramount++;
    }

    private void generateCards()
    {
        List<Integer> num = new ArrayList<>();
        int id = 1;
        for (int t=0; t <= 1; t++)
        {
            for (int x=1; x <= 9; x++) num.add(x);
        }

        for(int i=0; i < cards.length; i++)
        {
            for(int j=0; j < cards[i].length; j++)
            {
                Card card = new Card(num.get(0), new Coordinate(i, j), id);
                cards[i][j] = card;
                id++;
                num.remove(0);
            }
        }
        shuffleCards();
    }

    private void shuffleCards()
    {
        Random random = new Random();

        for (int i = cards.length - 1; i > 0; i--) {
            for (int j = cards[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                Card tempcard = cards[i][j];
                cards[i][j] = cards[m][n];
                cards[m][n] = tempcard;
            }
        }
    }
}

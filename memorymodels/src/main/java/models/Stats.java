package models;

public class Stats {
    private int winAmount;
    private int drawAmount;
    private int loseAmount;

    public Stats(int winamount, int drawamount, int loseamount){
        this.winAmount = winamount;
        this.drawAmount = drawamount;
        this.loseAmount = loseamount;
    }

    public int getWinAmount() {
        return winAmount;
    }

    public int getDrawAmount() {
        return drawAmount;
    }

    public int getLoseAmount() {
        return loseAmount;
    }
}

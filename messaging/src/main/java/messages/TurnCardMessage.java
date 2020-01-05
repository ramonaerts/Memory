package messages;

public class TurnCardMessage {
    private int x;
    private int y;

    public TurnCardMessage(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

package messages;

import models.Coordinate;

public class CardInfoMessage {
    private int cardValue;
    private Coordinate coordinate;
    private boolean firstPlayer;

    public CardInfoMessage(int cardValue, Object coordinate, boolean firstPlayer) {
        this.cardValue = cardValue;
        this.coordinate = (Coordinate) coordinate;
        this.firstPlayer = firstPlayer;
    }

    public int getCardValue() {
        return cardValue;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean getFirstPlayer() {
        return firstPlayer;
    }
}

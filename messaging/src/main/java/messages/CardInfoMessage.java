package messages;

import models.Coordinate;

public class CardInfoMessage {
    private int cardValue;
    private Coordinate coordinate;
    private int playerNr;

    public CardInfoMessage(int cardValue, Object coordinate, int playerNr) {
        this.cardValue = cardValue;
        this.coordinate = (Coordinate) coordinate;
        this.playerNr = playerNr;
    }

    public int getCardValue() {
        return cardValue;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getPlayerNr() {
        return playerNr;
    }
}

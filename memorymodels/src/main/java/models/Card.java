package models;

import enums.CardState;

public class Card {
    private int cardID;
    private CardState cardState;
    private int value;
    private Coordinate coordinate;
    private int turnedBy;

    public Card(int value, int id){
        this.value = value;
        this.cardID = id;
        this.cardState = CardState.HIDDEN;
        this.turnedBy = 0;
    }

    public int getCardID() {
        return cardID;
    }

    public int getValue() {
        return value;
    }

    public CardState getCardState() {
        return cardState;
    }

    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }

    public int getTurnedBy() {
        return turnedBy;
    }

    public void setTurnedBy(int turnedBy) {
        this.turnedBy = turnedBy;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}

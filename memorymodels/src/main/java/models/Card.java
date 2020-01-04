package models;

import enums.CardState;

public class Card {
    private int cardID;
    private CardState cardState;
    private int value;
    private int turnAmount;
    private Coordinate coordinate;
    private int turnedBy;

    public Card(int value, Coordinate coordinate, int id){
        this.value = value;
        this.coordinate = coordinate;
        this.cardID = id;
        this.cardState = CardState.HIDDEN;
        this.turnAmount = 0;
        this.turnedBy = 0;
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
}

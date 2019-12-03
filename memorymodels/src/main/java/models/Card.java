package models;

import enums.CardState;

public class Card {
    private int cardID;
    private CardState cardState;
    private String value;
    private int turnAmount;
    private Coordinate coordinate;

    public Card(String value, Coordinate coordinate){
        this.value = value;
        this.coordinate = coordinate;
        this.cardState = CardState.HIDDEN;
        this.turnAmount = 0;
    }
}

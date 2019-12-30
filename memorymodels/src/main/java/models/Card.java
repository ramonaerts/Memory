package models;

import enums.CardState;

public class Card {
    private int cardID;
    private CardState cardState;
    private int value;
    private int turnAmount;
    private Coordinate coordinate;

    public Card(int value, Coordinate coordinate, int id){
        this.value = value;
        this.coordinate = coordinate;
        this.cardID = id;
        this.cardState = CardState.HIDDEN;
        this.turnAmount = 0;
    }
}

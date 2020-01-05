package messages;

import models.Coordinate;

public class TurnCardBackMessage {
    private Coordinate coordinate;

    public TurnCardBackMessage(Object coordinate) {
        this.coordinate = (Coordinate) coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}

package messages;

import enums.GameResult;

public class GameResultMessage {
    private GameResult result;

    public GameResultMessage(Object result) {
        this.result = (GameResult)result;
    }

    public GameResult getResult() {
        return result;
    }
}

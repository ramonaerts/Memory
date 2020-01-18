package logic;

import enums.CardState;
import models.Card;
import models.Player;
import org.apache.log4j.Logger;

public class CardTurner implements Runnable {

    private Game game;
    private Card card;
    private Player player;
    private static Logger log = Logger.getLogger(CardTurner.class.getName());

    public CardTurner(Game game, Card card, Player player) {
        this.game = game;
        this.card = card;
        this.player = player;
    }

    @Override
    public void run(){
        card.setTurnedBy(0);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info(e);
        }
        card.setCardState(CardState.HIDDEN);
        game.getGenerator().turnCardBack(card.getCoordinate(), player.getSessionID());

    }
}

package logic;

import enums.CardState;
import models.Card;
import models.Player;

public class CardTurner implements Runnable {

    private Game game;
    private Card card;
    private Player player;

    public CardTurner(Game game, Card card, Player player) {
        this.game = game;
        this.card = card;
        this.player = player;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e);
        }
        card.setCardState(CardState.HIDDEN);
        card.setTurnedBy(0);
        game.getGenerator().turnCardBack(card.getCoordinate(), player.getSessionID());

    }
}

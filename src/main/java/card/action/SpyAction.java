package main.java.card.action;

import main.java.card.Card;
import main.java.player.Player;

/**
 * Created by jon on 3/31/15.
 */
public class SpyAction implements Action {

    public SpyAction(Player[] players) {
        this.players = players;
    }

    public void onPlay(Player activePlayer) {
        Card card;
        for (Player player : players) {
            card = player.draw();

            // Note: the active player makes all of the decisions regarding keep/discard
            if (activePlayer.onSpy(player, card.id())) {
                player.putCardOnDeck(card);
            } else {
                player.discard(card);
            }
        }
    }

    private Player[] players;
}

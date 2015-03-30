package main.java.card.action;

import main.java.card.Card;
import main.java.core.Supply;
import main.java.player.Player;

/**
 * Created by jon on 3/30/15.
 */
public class BureaucratAction implements Action {

    public BureaucratAction(Player[] players) {
        this.players = players;
    }

    public void onPlay(Player activePlayer) {
        activePlayer.putCardOnDeck(activePlayer.getSupply().take(Supply.SILVER));

        for (Player player : players) {
            if (player == activePlayer) { continue; }
            if (player.handContainsType(Card.TYPE_VICTORY)) {
                // Isn't this chain lovely?
                player.putCardOnDeck(player.takeCardFromHand(player.onBureaucratAttack()));
            }
        }
    }

    private Player[] players;
}

package main.java.card.action;

import main.java.card.Card;
import main.java.core.Supply;
import main.java.core.player.Player;

/**
 * Created by jon on 1/26/15.
 */
public class WitchAction implements Action {

    private Player[] players;

    private Card curse;

    public WitchAction(Player[] players, Card curse) {
        this.players = players;
        this.curse = curse;
    }

    public void onPlay(Player actingPlayer) {
        actingPlayer.drawToHand(2);

        for (Player player : players) {
            if (player != actingPlayer) {
                player.gain(Supply.CURSE);
            }
        }
    }
}

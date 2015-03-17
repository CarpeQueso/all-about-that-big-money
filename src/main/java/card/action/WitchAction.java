package main.java.card.action;

import main.java.core.Supply;
import main.java.player.Player;

/**
 * Created by jon on 1/26/15.
 */
public class WitchAction implements Action {

    private Player[] players;

    public WitchAction(Player[] players) {
        this.players = players;
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

package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 3/23/15.
 */
public class ChancellorAction implements Action {

    public void onPlay(Player player) {
        player.addCoins(2);

        if (player.onChancellor()) {
            while (player.getDeckSize() > 0) {
                player.discard(player.draw());
            }
        }
    }
}

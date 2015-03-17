package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 1/26/15.
 */
public class FestivalAction implements Action {

    public void onPlay(Player player) {
        player.addActions(2);
        player.addBuys(1);
        player.addCoins(2);
    }
}

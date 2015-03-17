package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 1/26/15.
 */
public class MarketAction implements Action {

    public void onPlay(Player player) {
        player.drawToHand();
        player.addActions(1);
        player.addBuys(1);
        player.addCoins(1);
    }
}

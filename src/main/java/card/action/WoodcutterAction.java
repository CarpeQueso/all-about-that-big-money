package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 1/26/15.
 */
public class WoodcutterAction implements Action {

    public void onPlay(Player player) {
        player.addBuys(1);
        player.addCoins(2);
    }
}

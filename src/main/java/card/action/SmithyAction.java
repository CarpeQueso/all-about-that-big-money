package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 1/26/15.
 */
public class SmithyAction implements Action {

    public void onPlay(Player player) {
        player.drawToHand(3);
    }
}

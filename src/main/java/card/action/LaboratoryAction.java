package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 1/26/15.
 */
public class LaboratoryAction implements Action {

    public void onPlay(Player player) {
        player.drawToHand(2);
        player.addActions(1);
    }
}

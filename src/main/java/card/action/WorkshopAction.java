package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 1/27/15.
 */
public class WorkshopAction implements Action {

    public void onPlay(Player player) {
        player.gain(player.onGain(4));
    }
}

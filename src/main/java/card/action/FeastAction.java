package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 3/30/15.
 */
public class FeastAction implements Action {

    // As of right now, this card is trashed through game logic. Not sure if there's a better way to trash.
    public void onPlay(Player player) {
        player.onGain(5);
    }
}

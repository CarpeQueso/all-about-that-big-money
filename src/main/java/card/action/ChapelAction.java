package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 3/31/15.
 */
public class ChapelAction implements Action {

    public void onPlay(Player player) {
        int numCardsTrashed = 0;
        boolean[] trashDecisions = new boolean[player.handSize()];
        // Asking the player for decisions
        // Setting an array value to true means the player wants to trash the card at that position
        player.onChapel(trashDecisions);

        for (int i = trashDecisions.length - 1; i >= 0; i--) {
            if (trashDecisions[i] && numCardsTrashed <= 4) { // i.e. you want to discard this card
                player.trashFromHand(i);
                numCardsTrashed++;
            }
        }
    }
}

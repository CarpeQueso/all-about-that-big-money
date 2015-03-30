package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 1/27/15.
 */
public class CellarAction implements Action {

    //Todo this is much more straightforward than the previous version. Still, test.
    public void onPlay(Player player) {
        player.addActions(1);

        boolean[] discardDecisions = new boolean[player.handSize()];
        // Asking the player for decisions
        // Setting an array value to true means the player wants to discard the card at that position
        player.onCellar(discardDecisions);

        // Once the player has made a decision, discard the cards they specified.
        // The number of discarded cards is kept so that you know how many to draw later.
        int numCardsDiscarded = 0;

        for (int i = discardDecisions.length - 1; i >= 0; i--) {
            if (discardDecisions[i]) { // i.e. you want to discard this card
                player.discardFromHand(i);
                numCardsDiscarded++;
            }
        }

        player.drawToHand(numCardsDiscarded);
    }
}

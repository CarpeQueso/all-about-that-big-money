package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 1/27/15.
 */
public class CellarAction implements Action {

    //Todo this reeeeaaaally needs to be tested.
    public void onPlay(Player player) {
        player.addActions(1);

        boolean[] discardDecisions = new boolean[player.getHandSize()];
        // Asking the player for decisions
        // Setting an array value to true means the player wants to discard the card at that position
        player.onCellar(discardDecisions);

        // Once the player has made a decision, discard the cards they specified.
        int handIndex = 0;
        int numCardsDiscarded = 0;

        for (int i = 0; i < discardDecisions.length; i++) {
            if (discardDecisions[i]) { // i.e. you want to discard this card
                player.discardFromHand(handIndex);
                numCardsDiscarded++;
            } else {
                // Only increments if a card was not discarded. By the nature of the player's hand,
                // a discarded card will bring the next card down to the current index. Kind of a messy system, this..
                handIndex++;
            }
        }

        player.drawToHand(numCardsDiscarded);
    }
}

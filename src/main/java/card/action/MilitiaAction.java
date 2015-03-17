package main.java.card.action;

import main.java.player.Player;

import java.util.Arrays;

/**
 * Created by jon on 1/27/15.
 */
public class MilitiaAction implements Action {

    private Player[] players;

    public MilitiaAction(Player[] players) {
        this.players = players;
    }

    //Todo test this like crazy, yo.
    public void onPlay(Player activePlayer) {
        activePlayer.addCoins(2);

        // Attack!
        int[] keepCardIndices;
        for (Player player : players) {
            if (player != activePlayer) {
                keepCardIndices = player.onMilitiaAttack();
                Arrays.sort(keepCardIndices);

                int handIndex = 0;
                int keepIndex = 0;
                int numCardsDiscarded = 0;
                for (int i = 0; i < player.getHandSize(); i++) {
                    if (keepIndex < keepCardIndices.length) {
                        if (handIndex != keepCardIndices[keepIndex] - numCardsDiscarded) {
                            player.discardFromHand(handIndex);
                            numCardsDiscarded++;
                        } else { // We want to keep this card
                            // Increment and move along
                            handIndex++;
                            keepIndex++;
                        }
                    } else {
                        player.discardFromHand(handIndex);
                        // Increment for consistency's sake. Unnecessary at this point.
                        numCardsDiscarded++;
                    }
                }
            }
        }
    }
}

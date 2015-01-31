package main.java.card.action;

import main.java.card.Card;
import main.java.core.player.Player;

/**
 * Created by jon on 1/26/15.
 */
public class AdventurerAction implements Action {

    public void onPlay(Player player) {
        int treasureCount = 0;
        Card drawnCard;

        while (treasureCount < 2) {
            // Be careful that you don't lose any cards you get from the player!
            drawnCard = player.draw();

            if (drawnCard.getType() == Card.TYPE_TREASURE) {
                player.addCardToHand(drawnCard);
                treasureCount++;
            } else {
                player.discard(drawnCard);
            }
        }
    }
}

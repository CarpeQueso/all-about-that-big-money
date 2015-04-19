package main.java.card.action;

import main.java.card.Card;
import main.java.player.Player;

import java.util.ArrayList;

/**
 * Created by jon on 1/26/15.
 */
public class AdventurerAction implements Action {

    public void onPlay(Player player) {
        int treasureCount = 0;
        Card drawnCard;

        ArrayList<Card> nonTreasureCards = new ArrayList<Card>();

        while (treasureCount < 2) {
            // Be careful that you don't lose any cards you get from the player!
            drawnCard = player.draw();

            if (drawnCard == null) {
                break;
            }

            if (drawnCard.getType() == Card.TYPE_TREASURE) {
                player.addCardToHand(drawnCard);
                treasureCount++;
            } else {
                // Set aside, but don't discard.
                nonTreasureCards.add(drawnCard);
            }
        }
        // Discard set aside cards
        for (Card card : nonTreasureCards) {
            player.discard(card);
        }
        nonTreasureCards.clear();
    }
}

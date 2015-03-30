package main.java.card.action;

import main.java.card.Card;
import main.java.player.Player;

/**
 * Created by jon on 3/30/15.
 */
public class LibraryAction implements Action {

    public void onPlay(Player player) {
        Card card;
        while (player.handSize() < 7) {
            card = player.draw();
            if (card == null) { break; } // You've run out of cards to draw.
            if (card.getType() == Card.TYPE_ACTION || card.getType() == Card.TYPE_REACTION) {
                if (player.onLibrary(card)) { // A true value means the player wants to keep the card.
                    player.addCardToHand(card);
                } else {
                    player.discard(card);
                }
            } else {
                player.addCardToHand(card);
            }
        }
    }
}

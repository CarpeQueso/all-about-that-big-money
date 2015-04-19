package main.java.card.action;

import main.java.card.Card;
import main.java.player.Player;

/**
 * Created by jon on 4/6/15.
 */
public class ThiefAction implements Action {

    public ThiefAction(Player[] players) {
        this.players = players;
    }

    /**
     * WARNING: this action is implemented specifically for the two player game. Need modification for use with
     * more than two players.. (only slight modification; the active player should be able to choose from a pool of all
     * to-be-trashed cards
     *
     * @param activePlayer
     */
    public void onPlay(Player activePlayer) {
        Card firstCard, secondCard, selectedCard;
        int selectedCardID;
        for (Player player : players) {
            if (player == activePlayer) { continue; }
            //Todo check for nulls? unlikely that either will be null, but who knows?
            firstCard = player.draw();
            secondCard = player.draw();

            // DON'T DROP ANY CARDS! If you haven't selected the card to keep/trash, discard it back to the player.
            if (firstCard.getType() == Card.TYPE_TREASURE && secondCard.getType() == Card.TYPE_TREASURE) {
                selectedCardID = activePlayer.onThiefSelect(firstCard.id(), secondCard.id());
                if (selectedCardID == firstCard.id()) {
                    selectedCard = firstCard;
                    player.discard(secondCard);
                } else {
                    selectedCard = secondCard;
                    player.discard(firstCard);
                }
            } else if (firstCard.getType() == Card.TYPE_TREASURE) {
                selectedCard = firstCard;
                player.discard(secondCard);
            } else if (secondCard.getType() == Card.TYPE_TREASURE) {
                selectedCard = secondCard;
                player.discard(firstCard);
            } else {
                player.discard(firstCard);
                player.discard(secondCard);
                continue;
            }

            // Decide to keep or trash selected card
            if (activePlayer.onThiefGain(selectedCard.id())) {
                activePlayer.gain(selectedCard);
            } else {
                player.trash(selectedCard);
            }

        }
    }

    private Player[] players;
}

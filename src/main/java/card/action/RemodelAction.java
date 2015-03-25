package main.java.card.action;

import main.java.card.Card;
import main.java.player.Player;

/**
 * Created by jon on 1/28/15.
 */
public class RemodelAction implements Action {

    public void onPlay(Player player) {
        // Trash a card and get its cost
        Card trashCard = player.takeCardFromHand(player.onRemodelTrash());
        int trashCardCost = trashCard.getCost();
        player.trash(trashCard);

        // Gain a card costing up to 2 more than the trashed card
        player.gain(player.onGain(trashCardCost + 2));
    }
}

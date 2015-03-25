package main.java.card.action;

import main.java.card.Card;
import main.java.player.Player;

/**
 * Created by jon on 1/28/15.
 */
public class MineAction implements Action {

    public void onPlay(Player player) {
        // Trash a treasure card and get its cost
        Card trashCard = player.takeCardFromHand(player.onMineTrash());
        int trashCardCost = trashCard.getCost();
        player.trash(trashCard);

        // Gain a treasure card costing up to 3 more than the trashed card
        player.gainToHand(player.onMineGain(trashCardCost + 3));
    }
}

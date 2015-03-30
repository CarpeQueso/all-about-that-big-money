package main.java.card.action;

import main.java.core.Game;
import main.java.player.Player;

/**
 * Created by jon on 3/30/15.
 *
 * We're going to go ahead and make the assumption that if this card is played, the player wants to trash a copper.
 * Therefore, if a copper is found, it will be trashed. Otherwise, the player gets no beneficial effects from the card.
 */
public class MoneylenderAction implements Action {

    public void onPlay(Player player) {
        int copperIndex = player.findCardInHand(Game.COPPER_ID);
        if (copperIndex != -1) {
            player.trashFromHand(copperIndex);
            player.addCoins(3);
        }
    }
}

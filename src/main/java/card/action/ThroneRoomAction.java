package main.java.card.action;

import main.java.card.Card;
import main.java.player.Player;

/**
 * Created by jon on 3/30/15.
 */
public class ThroneRoomAction implements Action {

    public void onPlay(Player player) {
        int handIndex = player.onThroneRoom();
        Card card = player.takeCardFromHand(handIndex);

        // Play card twice.
        card.onPlay(player);
        card.onPlay(player);

        // Add card to active pile manually since we're bypassing normal play functionality.
        player.addCardToActivePile(card);
    }
}

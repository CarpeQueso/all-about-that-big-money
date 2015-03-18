package main.java.util.messaging;

import main.java.card.Card;
import main.java.player.Player;

/**
 * Created by jon on 3/17/15.
 */
public interface Receiver {

    //Todo abstract later if necessary
    void onNotify(Player activePlayer, PlayerEvent event, Card card);
}

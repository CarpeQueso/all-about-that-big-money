package main.java.card.value;

import main.java.player.Player;

/**
 * Created by jon on 1/21/15.
 */
public class GardensValue implements Value {

    /**
     * This will produce inaccurate result until all cards are
     * shuffled into the deck at the end of the game.
     * @param player
     * @return
     */
    //Todo decide if gardens should produce accurate results all the time.
    public int getValue(Player player) {
        return player.deckSize() / 10;
    }
}

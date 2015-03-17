package main.java.card.value;

import main.java.player.Player;

/**
 * Created by jon on 1/21/15.
 */
public class SimpleValue implements Value {

    public SimpleValue(int value) {
        this.value = value;
    }

    public int getValue(Player player) {
        // Do nothing with player.
        return value;
    }

    private int value;
}

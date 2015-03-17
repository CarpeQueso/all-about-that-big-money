package main.java.util.messaging;

import main.java.player.Player;

/**
 * Created by jon on 3/17/15.
 */
public class Microphone {

    public Microphone(Channel channel) {
        this.channel = channel;
    }

    //Todo abstract later if necessary
    public void say(Player activePlayer, PlayerEvent event, int cardID) {
        channel.broadcast(activePlayer, event, cardID);
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    private Channel channel;
}

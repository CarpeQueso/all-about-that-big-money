package main.java.util.messaging;

import main.java.player.Player;

import java.util.ArrayList;

/**
 * Created by jon on 3/17/15.
 */
public class Channel {

    public Channel() {
        receivers = new ArrayList<Receiver>();
    }

    public void addReceiver(Receiver receiver) {
        receivers.add(receiver);
    }

    public void removeReceiver(Receiver receiver) {
        receivers.remove(receiver);
    }

    public void broadcast(Player activePlayer, PlayerEvent event, int cardID) {
        for (Receiver receiver : receivers) {
            receiver.onNotify(activePlayer, event, cardID);
        }
    }

    private ArrayList<Receiver> receivers;
}

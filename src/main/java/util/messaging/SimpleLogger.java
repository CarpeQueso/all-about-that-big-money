package main.java.util.messaging;

import main.java.card.Card;
import main.java.player.Player;

import java.io.PrintStream;

/**
 * Created by jon on 3/18/15.
 */
public class SimpleLogger implements Receiver {

    public SimpleLogger(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void onNotify(Player activePlayer, PlayerEvent event, Card card) {
        printStream.print(activePlayer.getClass().getSimpleName() + " ");
        switch (event) {
            case GAIN: printStream.print("gained "); break;
            case PLAY: printStream.print("played "); break;
            case TRASH: printStream.print("trashed "); break;
        }
        printStream.println(card.getName());
    }

    private PrintStream printStream;
}

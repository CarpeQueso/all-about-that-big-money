package main.java.player;

import main.java.card.Card;
import main.java.core.Supply;
import main.java.util.messaging.PlayerEvent;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by jon on 1/27/15.
 */
public class HumanPlayer extends Player {



    public HumanPlayer(PrintStream stream) {
        this.stream = stream;
        this.input = new Scanner(System.in);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void onActionPhase() {
        stream.println("--------------");
        stream.println(" Action Phase");
        stream.println("--------------");
        int handIndex;
        while (this.getAvailableActions() > 0 && this.handContainsType(Card.TYPE_ACTION)) {
            stream.print("Available actions: ");
            stream.println(this.getAvailableActions());
            this.printHand();
            stream.print("Choose a card to play: ");
            handIndex = input.nextInt();
            if (handIndex >= 0 && handIndex < this.getHandSize()) {
                this.play(handIndex);
            } else {
                stream.println("That hand index is invalid. Try again.");
            }
        }
    }

    @Override
    public void onBuyPhase() {
        stream.println("-----------");
        stream.println(" Buy Phase");
        stream.println("-----------");
        //Todo print in supply method or something else. This feels weird.
        stream.println(supply.supplyCards());
        int supplyIndex;
        while (this.getAvailableBuys() > 0 && this.getAvailableCoins() > 0) {
            stream.print("Available buys: ");
            stream.println(this.getAvailableBuys());
            stream.print("Available coins: ");
            stream.println(this.getAvailableCoins());
            stream.print("Choose a card to buy: ");
            supplyIndex = input.nextInt();
            if (supplyIndex == -1) {
                break;
            }
            if (supplyIndex >= 0 && supplyIndex < Supply.TOTAL_SUPPLY_CARDS) {
                if (!this.buy(supplyIndex)) { // This may be a stupid way to do this..
                    stream.println("Card cannot be bought. Choose another.");
                }
            } else {
                stream.println("That hand index is invalid. Try again.");
            }
        }
    }

    public void onNotify(Player activePlayer, PlayerEvent event, Card card) {

    }

    @Override
    public boolean react() {
        stream.println("You've been attacked! Do you want to reveal the reaction card in your hand? [y/n]");
        return input.next().trim().toLowerCase().charAt(0) == 'y';
    }

    @Override
    public void onCellar(boolean[] discardDecisions) {

    }

    @Override
    public boolean onChancellor() {
        return true;
    }

    @Override
    public int onWorkshop() {
        return 0;
    }

    @Override
    public int[] onMilitiaAttack() {
        return new int[0];
    }

    @Override
    public int onRemodelTrash() {
        return 0;
    }

    @Override
    public int onRemodelGain(int costLimit) {
        return 0;
    }

    @Override
    public int onMineTrash() {
        return 0;
    }

    @Override
    public int onMineGain(int costLimit) {
        return 0;
    }

    public void printHand() {
        stream.println("Hand:");
        Card card;
        for (int i = 0; i < hand.size(); i++) {
            card = hand.get(i);
            stream.print(i + "\t");
            stream.println(card.getName());
        }
    }

    private PrintStream stream;

    private Scanner input;
}

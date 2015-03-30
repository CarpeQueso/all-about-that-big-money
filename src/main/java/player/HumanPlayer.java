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
            if (handIndex == -1) { break; }
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
        stream.println("How many cards do you want to discard?");
        int numCardsToDiscard = input.nextInt();

        printHand();
        stream.println("Enter the indices of each card to discard.");
        for (int i = 0; i < numCardsToDiscard; i++) {
            discardDecisions[input.nextInt()] = true;
        }
    }

    @Override
    public boolean onChancellor() {
        stream.println("Do you want to place your deck into your discard pile? [y/n]");
        return input.next().trim().toLowerCase().charAt(0) == 'y';
    }

    @Override
    public int[] onMilitiaAttack() {
        return new int[0];
    }

    @Override
    public int onRemodelTrash() {
        printHand();
        stream.println("Which card do you want to remodel?");
        int handIndex = input.nextInt();
        while (handIndex < 0 || handIndex >= this.getHandSize()) {
            stream.println("This index is out of bounds. Choose another..");
            handIndex = input.nextInt();
        }

        return handIndex;
    }

    @Override
    public int onGain(int costLimit) {
        stream.println(supply.supplyCards());
        stream.println("Choose a card of cost " + costLimit + " or below to gain.");
        int supplyIndex = input.nextInt();
        while (supplyIndex < 0 || supplyIndex >= Supply.TOTAL_SUPPLY_CARDS
                || supply.view(supplyIndex).getCost() > costLimit) {
            stream.println("This index is out of bounds or the card is over the cost limit. Choose another..");
            supplyIndex = input.nextInt();
        }

        return supplyIndex;
    }

    @Override
    public int onMineTrash() {
        printHand();
        stream.println("Which treasure card do you want to trash?");
        int handIndex = input.nextInt();
        while (handIndex < 0 || handIndex >= this.getHandSize()
                || hand.get(handIndex).getType() != Card.TYPE_TREASURE) {
            stream.println("This index is out of bounds or the card is not a treasure card. Choose another..");
            handIndex = input.nextInt();
        }

        return handIndex;
    }

    @Override
    public int onMineGain(int costLimit) {
        stream.println(supply.supplyCards());
        stream.println("Choose a card of cost " + costLimit + " or below to gain.");
        int supplyIndex = input.nextInt();
        while (supplyIndex < 0 || supplyIndex >= Supply.TOTAL_SUPPLY_CARDS
                || supply.view(supplyIndex).getType() != Card.TYPE_TREASURE
                || supply.view(supplyIndex).getCost() > costLimit) {
            stream.println("This index is out of bounds, the card is not a treasure card, or it is above the cost limit. Choose another..");
            supplyIndex = input.nextInt();
        }

        return supplyIndex;
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

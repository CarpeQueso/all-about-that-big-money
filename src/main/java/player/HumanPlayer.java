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
    public void setup() {
        //Todo this is TEMPORARY. Make better later
        supply.sortKingdomCardsByCost();
    }

    @Override
    public void onActionPhase() {
        stream.println("--------------");
        stream.println(" Action Phase");
        stream.println("--------------");
        int handIndex;
        while (this.availableActions() > 0 && this.handContainsType(Card.TYPE_ACTION)) {
            stream.print("Available actions: ");
            stream.println(this.availableActions());
            this.printHand();
            stream.print("Choose a card to play: ");
            handIndex = input.nextInt();
            if (handIndex == -1) { break; }
            if (handIndex >= 0 && handIndex < this.handSize()) {
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
        while (this.availableBuys() > 0 && this.availableCoins() > 0) {
            stream.print("Available buys: ");
            stream.println(this.availableBuys());
            stream.print("Available coins: ");
            stream.println(this.availableCoins());
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
    public void onCellar(final boolean[] discardDecisions) {
        stream.println("How many cards do you want to discard?");
        int numCardsToDiscard = input.nextInt();

        printHand();
        stream.println("Enter the indices of each card to discard.");
        for (int i = 0; i < numCardsToDiscard; i++) {
            discardDecisions[input.nextInt()] = true;
        }
    }

    @Override
    public void onChapel(boolean[] trashDecisions) {
        stream.println("How many cards do you want to trash?");
        int numCardsToTrash = input.nextInt();

        printHand();
        stream.println("Enter the indices of each card to discard.");
        for (int i = 0; i < numCardsToTrash; i++) {
            trashDecisions[input.nextInt()] = true;
        }
    }

    @Override
    public boolean onChancellor() {
        stream.println("Do you want to place your deck into your discard pile? [y/n]");
        return input.next().trim().toLowerCase().charAt(0) == 'y';
    }

    @Override
    public int onBureaucratAttack() {
        printHand();
        stream.println("You've been attacked! Which victory card do you want to place onto your deck?");
        int handIndex = input.nextInt();
        while (handIndex < 0 || handIndex >= this.handSize() || hand.get(handIndex).getType() != Card.TYPE_VICTORY) {
            stream.println("This index is out of bounds or the card is not a victory card. Choose another..");
            handIndex = input.nextInt();
        }

        return handIndex;
    }

    @Override
    public void onMilitiaAttack(final int[] cardsToKeep) {
        printHand();
        stream.println("You've been attacked! Choose three cards to keep!");
        // Fill array with indices of cards to keep.
        for (int i = 0; i < cardsToKeep.length; i++) {
            int handIndex = input.nextInt();
            while (handIndex < 0 || handIndex >= this.handSize()) {
                stream.println("This index is out of bounds. Choose another..");
                handIndex = input.nextInt();
            }
            cardsToKeep[i] = handIndex;
        }
    }

    @Override
    public int onRemodelTrash() {
        printHand();
        stream.println("Which card do you want to remodel?");
        int handIndex = input.nextInt();
        while (handIndex < 0 || handIndex >= this.handSize()) {
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
    public boolean onSpy(Player player, int cardID) {
        if (this == player) {
            stream.print("Spy Action! The revealed card is: " + supply.view(cardID).getName() + "\n");
            stream.println("Do you want to put this card back onto your deck? [y/n]");
            return input.next().trim().toLowerCase().charAt(0) == 'y';
        } else {
            stream.print("Spy Action! The revealed card is: " + supply.view(cardID).getName() + "\n");
            stream.println("Do you want to put this card back onto your opponent's deck? [y/n]");
            return input.next().trim().toLowerCase().charAt(0) == 'y';
        }
    }

    @Override
    public int onThiefSelect(int firstCardID, int secondCardID) {
        stream.println("Thief select! Choose one of the two cards from your opponent's deck to trash...");
        stream.println("0 - " + supply.view(firstCardID).getName());
        stream.println("1 - " + supply.view(secondCardID).getName());
        stream.println("Enter 0 for the first card, 1 for the second.");
        if (input.nextInt() == 0) {
            return firstCardID;
        } else {
            return secondCardID;
        }
    }

    @Override
    public boolean onThiefGain(int cardID) {
        stream.println("Do you want to gain this card? [y/n]");
        stream.println(supply.view(cardID).getName());
        return input.next().trim().toLowerCase().charAt(0) == 'y';
    }

    @Override
    public int onThroneRoom() {
        printHand();
        stream.println("Which card do you want to play twice?");
        int handIndex = input.nextInt();
        while (handIndex < 0 || handIndex >= this.handSize()) {
            stream.println("This index is out of bounds. Choose another..");
            handIndex = input.nextInt();
        }

        return handIndex;
    }

    @Override
    public boolean onLibrary(Card actionCard) {
        stream.println("You drew " + actionCard.getName() + ". Do you want to keep it? [y/n]");
        return input.next().trim().toLowerCase().charAt(0) == 'y';
    }

    @Override
    public int onMineTrash() {
        printHand();
        stream.println("Which treasure card do you want to trash?");
        int handIndex = input.nextInt();
        while (handIndex < 0 || handIndex >= this.handSize()
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

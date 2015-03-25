package main.java.player;

import main.java.card.Card;
import main.java.core.Game;
import main.java.core.Supply;
import main.java.util.messaging.Microphone;
import main.java.util.messaging.PlayerEvent;
import main.java.util.messaging.Receiver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by jon on 1/16/15.
 */
public abstract class Player implements Receiver {

    protected Supply supply;

    protected ArrayList<Card> hand;

    private LinkedList<Card> deck;

    // Cards discarded and awaiting reshuffle (when deck runs out)
    private ArrayList<Card> discardPile;

    // Cards that have been played traditionally
    private ArrayList<Card> activePile;

    //Todo add a revealed pile to help with odd interactions

    private Microphone microphone;

    private int turnsTaken;

    private int availableActions;

    private int availableBuys;

    private int availableCoins;

    public Player(/*Supply supply*/) {
        //this.supply = supply;
        hand = new ArrayList<Card>();
        deck = new LinkedList<Card>();
        // These are initialized to size 40 so that (hopefully) they
        // only have to be resized once, if at all.
        discardPile = new ArrayList<Card>(40);
        activePile = new ArrayList<Card>(40);

        turnsTaken = 0;
    }

    /**
     * Assumes that reset will be called after game completion. At this point, all cards this player
     * possesses are guaranteed to be in the deck. As such, none of the other card piles (Active Pile, Discard Pile,
     * Hand, etc.) are emptied. The deck is emptied and the number of turns the player has taken is set to 0.
     */
    public void reset() {
        deck.clear(); // Remove all cards this player has to prep for new game.
        turnsTaken = 0;
    }

    /**
     * Removes and returns the top card from the deck. This is useful when actions have conditional card draw
     * and the returned card may be discarded instead of being added to the hand. If the deck is empty, this
     * method will try to shuffle the discard pile into the deck.
     *
     * This method should only be used by appropriate action cards. Do not
     * use this in classes that extend player.
     *
     * @return the drawn card from the deck, or null if both the deck and discard pile are empty
     *         (i.e. the player has played all playable cards in their deck)
     */
    public Card draw() {
        if (!deck.isEmpty()) {
            return deck.poll();
        } else if (!discardPile.isEmpty()) {
            shuffleDiscardPileIntoDeck();
            return draw();
        } else {
            return null;
        }
    }

    /**
     * drawToHand is a convenience method for card draw that goes straight into the hand. If the deck is empty, this
     * method will try to shuffle the discard pile into the deck.
     *
     *
     * This method should not be used by classes extending Player. It must be public to allow
     * action cards to affect the player appropriately.
     */
    //Todo decide if these need to return a boolean value
    public boolean drawToHand() {
        if (!deck.isEmpty()) {
            hand.add(deck.poll());
            return true;
        } else if (!discardPile.isEmpty()) {
            shuffleDiscardPileIntoDeck();
            // Try again
            return drawToHand();
        } else {
            return false; // No cards could be drawn and the deck could not be reshuffled.
        }
    }

    //Todo if you enter a value <= 0, this method returns true. Decide if bug or feature.
    public boolean drawToHand(int numCards) {
        for (int i = 0; i < numCards; i++) {
            if (!drawToHand()) { return false; }
        }
        return true;
    }

    public void shuffleDiscardPileIntoDeck() {
        // Place a random card from the discard pile back into the
        // deck until the discard pile is empty
        while (!discardPile.isEmpty()) {
            deck.add(
                    discardPile.remove((int) Math.round(Math.random() * (discardPile.size() - 1)))
            );
        }
    }

    public int findCardInHand(Card card) {
        return hand.indexOf(card);
    }

    public int findCardInHand(int cardID) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).id() == cardID) {
                return i;
            }
        }
        return -1;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public Card takeCardFromHand(int cardIndex) {
        return hand.remove(cardIndex);
    }

    public boolean gain(int supplyIndex) {
        if (supply.getNumCardsRemaining(supplyIndex) > 0) {
            discard(supply.take(supplyIndex));
            microphone.say(this, PlayerEvent.GAIN, supply.view(supplyIndex));
            return true;
        }
        return false;
    }

    public boolean gainToHand(int supplyIndex) {
        if (supply.getNumCardsRemaining(supplyIndex) > 0) {
            addCardToHand(supply.take(supplyIndex));
            microphone.say(this, PlayerEvent.GAIN, supply.view(supplyIndex));
            return true;
        }
        return false;
    }

    /**
     * Note: this method WILL remove the card to play from the player's hand.
     *
     * Pre: The card to be played is in this player's hand.
     *
     * @param handIndex the card in hand to be played
     */
    //Todo refactor eventually. Too many repeated statements..
    protected void play(int handIndex) {
        Card playedCard = hand.remove(handIndex);
        if (playedCard.getType() == Card.TYPE_TREASURE) {
            availableCoins += playedCard.getValue(this);
            activePile.add(playedCard);
            microphone.say(this, PlayerEvent.PLAY, playedCard);
        } else if (playedCard.getType() == Card.TYPE_ACTION
                || playedCard.getType() == Card.TYPE_REACTION) {
            if (availableActions > 0) {
                //Todo continue to be cognisant of whether adding to activePile at the end is an issue.
                availableActions--;

                playedCard.onPlay(this); // resolve action
                // Yes I hate myself for doing this. Yes I'm doing it anyway..
                if (playedCard.id() == Game.FEAST_ID) {
                    trash(playedCard);
                } else {
                    activePile.add(playedCard);
                }
                microphone.say(this, PlayerEvent.PLAY, playedCard);
            } else {
                // You ran out of actions. Do nothing.
            }
        } else if (playedCard.getType() == Card.TYPE_VICTORY) {
            // Do nothing
            //Todo decide if you'll ever need this condition
        }
    }

    /**
     * Call before buy phase in takeTurn, but after action phase. That distinction is important.
     */
    public void playAllTreasure() {
        for (int i = hand.size() - 1; i >= 0; i--) {
            if (hand.get(i).getType() == Card.TYPE_TREASURE) {
                play(i);
            }
        }
    }

    /**
     * Attempts to buy the specified card from the kingdom and add to discard pile.
     * Will NOT modify state if buy is unsuccessful.
     *
     * @param supplyIndex the position in the supply of the card to be bought
     * @return true if the buy is successful, false otherwise
     */
    public boolean buy(int supplyIndex) {
        if (supply.getNumCardsRemaining(supplyIndex) == 0) {
            return false;
        }
        Card card = supply.view(supplyIndex);

        // Explicit else statements remain for clarity.
        //Todo decide if this check should remain or if you only need to check for available buys elsewhere
        if (availableBuys > 0) {
            if (card.getCost() <= getAvailableCoins()) {
                gain(supplyIndex);
                availableCoins -= card.getCost();
                availableBuys--;
                return true;
            } else {
                // Not enough money to buy.
                return false;
            }
        } else {
            // You ran out of available buys.
            return false;
        }
    }

    public void discard(Card card) {
        discardPile.add(card);
    }

    /**
     * Beware that the index of each other card following will change after the given card is removed
     * @param cardIndex
     */
    public void discardFromHand(int cardIndex) {
        discard(hand.remove(cardIndex));
    }

    public void trash(Card card) {
        supply.trash(card);
        microphone.say(this, PlayerEvent.TRASH, card);
    }

    public void trashFromHand(int cardIndex) {
        trash(hand.remove(cardIndex));
    }

    public boolean handContainsType(int cardType) {
        for (Card card : hand) {
            if (card.getType() == cardType) {
                return true;
            }
        }
        return false;
    }

    public boolean handContainsCard(int cardID) {
        for (Card card : hand) {
            if (card.id() == cardID) {
                return true;
            }
        }
        return false;
    }

    /**
     * At the time this method is called, all aspects of the player and supply are guaranteed
     * to be initialized and properly populated. The extending class may do any preliminary work here
     * prior to the game beginning.
     */
    public abstract void setUp();

    public abstract void onActionPhase();

    public abstract void onBuyPhase();

    public void cleanUp() {
        // Removes from tail end so that each index isn't updated
        // with every removal. See ArrayList documentation.
        for (int i = hand.size() - 1; i >= 0; i--) {
            discardFromHand(i);
        }
        for (int i = activePile.size() - 1; i >= 0; i--) {
            discard(activePile.remove(i));
        }
        drawToHand(5);
        availableActions = 1;
        availableBuys = 1;
        availableCoins = 0;
    }

    public void takeTurn() {
        onActionPhase();
        playAllTreasure();
        onBuyPhase();
        cleanUp();
        turnsTaken++;
    }

    /**
     * All cards that this player possesses are added to the deck for easy end-game score calculation.
     * This method should not be used until the game has been officially ended.
     *
     * Note: Currently operating on the assumption that the active pile will be empty when this is called.
     * If this changes, update this method.
     */
    public void moveAllCardsToDeck() {
        for (int i = hand.size() - 1; i >= 0; i--) {
            deck.add(hand.remove(i));
        }
        for (int i = discardPile.size() - 1; i >= 0; i--) {
            deck.add(discardPile.remove(i));
        }
    }

    public int getTotalVictoryPointsInDeck() {
        int score = 0;
        Card card;
        for(Iterator<Card> iterator = deck.iterator(); iterator.hasNext();) {
            card = iterator.next();
            if (card.getType() == Card.TYPE_VICTORY) {
                score += card.getValue(this);
            }
        }
        return score;
    }

    // Not sure I like that this can just be passed around randomly. Rules may need to be established for what
    // you should and shouldn't do with it.
    public Supply getSupply() {
        return supply;
    }

    public int getAvailableActions() {
        return availableActions;
    }

    public int getAvailableBuys() {
        return availableBuys;
    }

    public int getAvailableCoins() {
        return availableCoins;
    }

    public int getNumTurnsTaken() {
        return turnsTaken;
    }

    public int getDeckSize() {
        return deck.size();
    }

    public int getHandSize() {
        return hand.size();
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public void setMicrophone(Microphone microphone) {
        this.microphone = microphone;
    }

    public void addActions(int numActions) {
        availableActions += numActions;
    }

    public void addBuys(int numBuys) {
        availableBuys += numBuys;
    }

    public void addCoins(int numCoins) {
        availableCoins += numCoins;
    }

    public abstract void onNotify(Player activePlayer, PlayerEvent event, Card card);

    /**
     * If this method is called, the player is being attacked AND has a reaction card in hand.
     * The player can choose to react and stop the incoming attack or not.
     *
     * @return true if this player wants to block an incoming attack, otherwise false
     */
    public abstract boolean react();

    // Note: true means you should discard the card at that index in the hand
    public abstract void onCellar(boolean[] discardDecisions);

    /**
     * Decide whether to put deck in discard pile.
     * @return true if you want to put your deck into your discard pile
     */
    public abstract boolean onChancellor();

    /**
     *
     * @return an int array of size 3 containing the indices of cards you want to keep
     */
    public abstract int[] onMilitiaAttack();

    /**
     *
     * @return the index of the card from your hand you want to trash
     */
    public abstract int onRemodelTrash();

    /**
     * There are no hard rules preventing you from acquiring a card that is above the cost limit.
     * The param is simply used as a decision aid. Please follow the rules and select a card
     * with cost at or below the cost limit.
     *
     * @param costLimit the upper cost limit of the card that you gain from remodeling
     *
     * @return the supply pile index of the card you want to gain from the supply after trashing a card
     */
    public abstract int onGain(int costLimit);

    /**
     *
     * @return
     */
    public abstract int onMineTrash();

    /**
     *
     * @param costLimit
     * @return
     */
    public abstract int onMineGain(int costLimit);
}

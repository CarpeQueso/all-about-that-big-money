package main.java.core;

import main.java.card.Card;

import java.util.ArrayList;

/**
 * Created by jon on 1/16/15.
 */
public class Supply {

    public static final int TOTAL_CARD_TYPES = 17;

    public static final int KINGDOM_0 = 0;

    public static final int KINGDOM_1 = 1;

    public static final int KINGDOM_2 = 2;

    public static final int KINGDOM_3 = 3;

    public static final int KINGDOM_4 = 4;

    public static final int KINGDOM_5 = 5;

    public static final int KINGDOM_6 = 6;

    public static final int KINGDOM_7 = 7;

    public static final int KINGDOM_8 = 8;

    public static final int KINGDOM_9 = 9;

    public static final int COPPER = 10;

    public static final int SILVER = 11;

    public static final int GOLD = 12;

    public static final int ESTATE = 13;

    public static final int DUCHY = 14;

    public static final int PROVINCE = 15;

    public static final int CURSE = 16;


    private Pile[] supplyPiles;

    private Card[] kingdom;

    private ArrayList<Card> trash;

    private int numEmptyPiles;

    /**
     * Default constructor.
     */
    public Supply() {
        supplyPiles = new Pile[TOTAL_CARD_TYPES];
        trash = new ArrayList<Card>();

        numEmptyPiles = 0;
    }

    /**
     * Adds a new pile of cards to the supply. The given card will serve as a template
     * to generate new cards taken from the corresponding pile.
     *
     * @param card the template card to use for this pile
     * @param totalAvailableCards the number of cards in the pile
     */
    public void add(int supplyIndex, Card card, int totalAvailableCards) {
        supplyPiles[supplyIndex] = new Pile(card, totalAvailableCards);
    }

    /**
     * If the pile isn't empty, generates a new card of the specified type and
     * returns it. The number of cards in the pile is decreased by one.
     *
     * @param supplyIndex the supply pile you want to take from
     *
     * @return a new card from the specified pile
     */
    public Card take(int supplyIndex) {
        Pile pile = supplyPiles[supplyIndex];
        Card returnCard = pile.take();
        if (pile.getNumCardsRemaining() == 0) {
            numEmptyPiles++;
        }
        return returnCard;
    }

    /**
     * Returns the template card for viewing. This card should not be used in gameplay.
     *
     *
     */
    public Card view(int supplyIndex) {
        return supplyPiles[supplyIndex].view();
    }

    /**
     * Adds a card to the trash pile to be removed from play (except under special circumstances).
     *
     * @param card the card to be added to the trash pile
     */
    public void trash(Card card) {
        trash.add(card);
    }

    /**
     * Returns an array of reference cards representing the 10 kingdom cards available in this supply.
     * These cards should not be used for gameplay.
     *
     * @return an array of 10 reference cards representing the available kingdom cards.
     */
    public Card[] getAvailableKingdomCards() {
        if (kingdom == null) {
            kingdom = new Card[10];
            for (int i = 0; i < kingdom.length; i++) {
                kingdom[i] = supplyPiles[i].view();
            }
        }

        return kingdom;
    }

    public int getNumCardsRemaining(int supplyIndex) {
        return supplyPiles[supplyIndex].getNumCardsRemaining();
    }

    public int getNumEmptyPiles() {
        return numEmptyPiles;
    }


    class Pile {

        private int numCardsRemaining;

        private Card templateCard;

        public Pile(Card templateCard, int totalAvailableCards) {
            this.templateCard = templateCard;
            numCardsRemaining = totalAvailableCards;
        }

        public Card take() {
            if (numCardsRemaining > 0) {
                numCardsRemaining--;
                return Card.initializeCard(templateCard);
            }
            return null;
        }

        /**
         * Returns the template card. This card should only be used for reference and not for gameplay.
         *
         * @return the template card for this pile
         */
        public Card view() {
            return templateCard;
        }

        /**
         * Guaranteed to be non-negative.
         * @return the number of cards remaining [0-n]
         */
        public int getNumCardsRemaining() {
            return numCardsRemaining;
        }
    }
}

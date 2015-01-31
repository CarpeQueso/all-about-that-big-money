package main.java.card;

import main.java.card.action.Action;
import main.java.card.value.Value;
import main.java.core.player.Player;

/**
 * Created by jon on 1/16/15.
 */
public final class Card {

    public static final int TYPE_TREASURE = 0;

    public static final int TYPE_VICTORY = 1;

    public static final int TYPE_ACTION = 2;

    public static final int TYPE_REACTION = 3;

    private String name;

    private int cost;

    private int cardType;

    private Value value;

    private Action action;

    public static Card initializeTreasureCard(String name, int cost, Value value) {
        return new Card(TYPE_TREASURE, name, cost, value, null);
    }

    public static Card initializeVictoryCard(String name, int cost, Value value) {
        return new Card(TYPE_VICTORY, name, cost, value, null);
    }

    public static Card initializeActionCard(String name, int cost, Action action) {
        return new Card(TYPE_ACTION, name, cost, null, action);
    }

    public static Card initializeReactionCard(String name, int cost, Action action) {
        return new Card(TYPE_REACTION, name, cost, null, action);
    }

    public static Card initializeCard(Card card) {
        return new Card(card);
    }

    private Card(int cardType, String name, int cost, Value value, Action action) {
        this.cardType = cardType;
        this.name = new String(name);
        this.cost = cost;

        this.value = value;
        this.action = action;
    }

    private Card(Card cardToCopy) {
        this.cardType = cardToCopy.getType();
        this.name = new String(cardToCopy.getName());
        this.cost = cardToCopy.getCost();

        // These are intentionally shared between Card instances.
        // If a problem arises, copy constructors can be added.
        this.value = cardToCopy.value;
        this.action = cardToCopy.action;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getType() {
        return cardType;
    }

    /**
     * Some cards may need to have their values readily accessible throughout the game (like copper).
     * Other cards may change in value as the game progresses, only to be finally evaluated late in
     * the game or after the game's completion
     * @return
     */
    public int getValue(Player player) {
        if (value == null) { return 0; } // No monetary or victory point value. The card is probably an action card.
        return value.getValue(player);
    }

    /**
     *
     * @param player the active player who plays this card
     */
    public void onPlay(Player player) {
        // It shouldn't be the case that players use non-action cards as actions,
        // but the guard is there just in case.
        if (action != null) {
            action.onPlay(player);
        } else {
            // Do nothing
        }
    }

    //Todo determine if these are necessary anymore
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card)) {
            return false;
        }

        // Under the assumption that all cards with the same name are the same card.
        // This should be true unless new card construction has bugs.
        return this.getName().equals(((Card) o).getName());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
}

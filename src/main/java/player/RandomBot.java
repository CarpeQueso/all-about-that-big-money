package main.java.player;

import main.java.card.Card;
import main.java.core.Game;
import main.java.core.Supply;
import main.java.util.messaging.PlayerEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jon on 4/18/15.
 */
public class RandomBot extends Player {

    @Override
    public void setup() {
        random = new Random();
    }

    @Override
    public void onActionPhase() {
        ArrayList<Integer> actionCardIndices = new ArrayList<Integer>();
        while (this.availableActions() > 0 && this.handContainsType(Card.TYPE_ACTION)) {
            for (int i = 0; i < hand.size(); i++) {
                int type = hand.get(i).getType();
                if (type == Card.TYPE_ACTION || type == Card.TYPE_REACTION) {
                    actionCardIndices.add(i);
                }
            }
            int cardIndexToPlay = actionCardIndices.get(random.nextInt(actionCardIndices.size()));

            this.play(cardIndexToPlay);
            actionCardIndices.clear();
        }
    }

    @Override
    public void onBuyPhase() {
        ArrayList<Integer> affordableSupplyCardIndices = new ArrayList<Integer>(Supply.TOTAL_SUPPLY_CARDS);
        while (this.availableBuys() > 0) {
            // Add a flag representing a choice not to buy anything.
            affordableSupplyCardIndices.add(-1);
            // Find the rest of the affordable cards
            for (int i = 0; i < Supply.TOTAL_SUPPLY_CARDS; i++) {
                if (supply.view(i).getCost() <= this.availableCoins()) {
                    affordableSupplyCardIndices.add(i);
                }
            }

            int buyChoice = affordableSupplyCardIndices.get(random.nextInt(affordableSupplyCardIndices.size()));
            // The "no buy" flag was selected. Break out of buy phase altogether.
            if (buyChoice == -1) {
                break;
            }
            this.buy(buyChoice);
            affordableSupplyCardIndices.clear();
        }
    }

    @Override
    public void onNotify(Player activePlayer, PlayerEvent event, Card card) {

    }

    @Override
    public boolean react() {
        return random.nextBoolean();
    }

    @Override
    public void onCellar(boolean[] discardDecisions) {
        for (int i = 0; i < discardDecisions.length; i++) {
            discardDecisions[i] = random.nextBoolean();
        }
    }

    @Override
    public void onChapel(boolean[] trashDecisions) {
        for (int i = 0; i < trashDecisions.length; i++) {
            trashDecisions[i] = random.nextBoolean();
        }
    }

    @Override
    public boolean onChancellor() {
        return random.nextBoolean();
    }

    @Override
    public int onBureaucratAttack() {
        return this.findFirstCardOfType(Card.TYPE_VICTORY);
    }

    @Override
    public void onMilitiaAttack(int[] cardsToKeep) {
        ArrayList<Integer> handIndices = new ArrayList<Integer>();
        // fill with possible choices
        for (int i = 0; i < hand.size(); i++) {
            handIndices.add(i);
        }
        // make random selections
        for (int i = 0; i < cardsToKeep.length; i++) {
            cardsToKeep[i] = handIndices.remove(random.nextInt(handIndices.size()));
        }
    }

    @Override
    public int onRemodelTrash() {
        if (hand.size() == 0) {
            // Indicates that we don't want to trash a card and will get none of the benefits.
            return -1;
        }
        return random.nextInt(hand.size());
    }

    @Override
    public int onGain(int costLimit) {
        ArrayList<Integer> affordableSupplyCardIndices = new ArrayList<Integer>(Supply.TOTAL_SUPPLY_CARDS);
        // Find all affordable cards
        for (int i = 0; i < Supply.TOTAL_SUPPLY_CARDS; i++) {
            if (supply.view(i).getCost() <= costLimit) {
                affordableSupplyCardIndices.add(i);
            }
        }

        int buyChoice = affordableSupplyCardIndices.get(random.nextInt(affordableSupplyCardIndices.size()));

        return buyChoice;
    }

    @Override
    public boolean onSpy(Player player, int cardID) {
        return random.nextBoolean();
    }

    @Override
    public int onThiefSelect(int firstCardID, int secondCardID) {
        if (random.nextBoolean()) {
            return firstCardID;
        } else {
            return secondCardID;
        }
    }

    @Override
    public boolean onThiefGain(int cardID) {
        return random.nextBoolean();
    }

    @Override
    public int onThroneRoom() {
        if (handContainsType(Card.TYPE_ACTION)) {
            return findFirstCardOfType(Card.TYPE_ACTION);
        } else if (handContainsType(Card.TYPE_REACTION)) {
            return findFirstCardOfType(Card.TYPE_REACTION);
        }
        return -1;
    }

    @Override
    public boolean onLibrary(Card actionCard) {
        return random.nextBoolean();
    }

    @Override
    public int onMineTrash() {
        if (!handContainsType(Card.TYPE_TREASURE)) {
            return -1;
        }

        ArrayList<Integer> treasureCardIndices = new ArrayList<Integer>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getType() == Card.TYPE_TREASURE) {
                treasureCardIndices.add(i);
            }
        }
        return treasureCardIndices.get(random.nextInt(treasureCardIndices.size()));
    }

    @Override
    public int onMineGain(int costLimit) {
        if (costLimit == 6) {
            return Supply.GOLD;
        } else if (costLimit == 3) {
            return Supply.SILVER;
        } else {
            return Supply.COPPER;
        }
    }

    private Random random;
}

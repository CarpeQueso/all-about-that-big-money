package main.java.player;

import main.java.card.Card;
import main.java.core.Supply;
import main.java.util.messaging.PlayerEvent;
import main.java.util.messaging.Receiver;

/**
 * Created by jon on 3/24/15.
 */
public class IntegrationTestBot extends Player implements Receiver {

    @Override
    public void setup() {

    }

    @Override
    public void onActionPhase() {

    }

    @Override
    public void onBuyPhase() {
        while (this.availableBuys() > 0) {
            // Another one of those awful times that this code is terrible, but I'm going to write it anyway.
            int maxCost = this.availableCoins();
            outerLoop: for (int cost = maxCost; cost >= 0; cost--) {
                for (int i = Supply.KINGDOM_0; i < Supply.TOTAL_SUPPLY_CARDS; i++) {
                    if (supply.view(i).getCost() == cost) {
                        this.buy(i);
                        break outerLoop;
                    }
                }
            }
        }
    }

    @Override
    public void onNotify(Player activePlayer, PlayerEvent event, Card card) {

    }

    @Override
    public boolean react() {
        return false;
    }

    @Override
    public void onCellar(boolean[] discardDecisions) {

    }

    @Override
    public void onChapel(boolean[] trashDecisions) {

    }

    @Override
    public boolean onChancellor() {
        return false;
    }

    @Override
    public int onBureaucratAttack() {
        return 0;
    }

    @Override
    public void onMilitiaAttack(final int[] cardsToKeep) {

    }

    @Override
    public int onRemodelTrash() {
        return 0;
    }

    @Override
    public int onGain(int costLimit) {
        return 0;
    }

    @Override
    public boolean onSpy(Player player, int cardID) {
        return false;
    }

    @Override
    public int onThiefSelect(int firstCardID, int secondCardID) {
        return 0;
    }

    @Override
    public boolean onThiefGain(int cardID) {
        return false;
    }

    @Override
    public int onThroneRoom() {
        return 0;
    }

    @Override
    public boolean onLibrary(Card actionCard) {
        return false;
    }

    @Override
    public int onMineTrash() {
        return 0;
    }

    @Override
    public int onMineGain(int costLimit) {
        return 0;
    }
}

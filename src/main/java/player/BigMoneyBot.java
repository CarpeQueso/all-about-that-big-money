package main.java.player;

import main.java.card.Card;
import main.java.core.Supply;
import main.java.util.messaging.PlayerEvent;

/**
 * Created by jon on 1/28/15.
 */
public class BigMoneyBot extends Player {

    public BigMoneyBot(/*Supply supply*/) {
        super(/*supply*/);

        // Do something else?
    }

    @Override
    public void setup() {
        // Set up?
    }

    @Override
    public void onActionPhase() {
        // Do nothing
    }

    @Override
    public void onBuyPhase() {
        int availableCoins = this.availableCoins();
        //System.out.println("Has " + availableCoins + " to spend");

        if (availableCoins >= 8) {
            //System.out.println("Buys Province");
            buy(Supply.PROVINCE);
        } else if (availableCoins >= 6) {
            //System.out.println("Buys Gold");
            buy(Supply.GOLD);
        } else if (availableCoins >= 3) {
            //System.out.println("Buys Silver");
            buy(Supply.SILVER);
        }
    }

    @Override
    public void onNotify(Player activePlayer, PlayerEvent event, Card card) {

    }

    @Override
    public boolean react() {
        // Do nothing
        return false;
    }

    @Override
    public void onCellar(boolean[] discardDecisions) {
        // Do nothing
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
        // Do nothing
        return 0;
    }

    @Override
    public int onGain(int costLimit) {
        // Do nothing
        return 0;
    }

    @Override
    public boolean onSpy(Player player, int cardID) {
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
        // Do nothing
        return 0;
    }

    @Override
    public int onMineGain(int costLimit) {
        // Do nothing
        return 0;
    }
}

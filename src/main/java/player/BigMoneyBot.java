package main.java.player;

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
    public void setUp() {
        // Set up?
    }

    @Override
    public void onActionPhase() {
        // Do nothing
    }

    @Override
    public void onBuyPhase() {
        int availableCoins = this.getAvailableCoins();
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
    public void onNotify(Player activePlayer, PlayerEvent event, int cardID) {

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
    public int onWorkshop() {
        // Do nothing
        return 0;
    }

    @Override
    public int[] onMilitiaAttack() {
        // Do nothing
        return new int[0];
    }

    @Override
    public int onRemodelTrash() {
        // Do nothing
        return 0;
    }

    @Override
    public int onRemodelGain(int costLimit) {
        // Do nothing
        return 0;
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

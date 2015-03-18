package main.java.player;

import main.java.card.Card;
import main.java.core.Supply;
import main.java.util.messaging.PlayerEvent;

/**
 * Created by jon on 1/31/15.
 */
public class BigMoneySmithyBot extends Player {

    private int numSmithies;

    public BigMoneySmithyBot() {
        super();

        numSmithies = 0;
    }

    @Override
    public void setUp() {
        //Todo search for smithy card and find index so you don't have to rely on a specific position.
    }

    @Override
    public void onActionPhase() {
        //Todo hacky again. Make more robust later.
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getType() == Card.TYPE_ACTION) {
                play(i);
            }
        }
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
        } else if (availableCoins >= 4 && numSmithies < 1) {
            //Todo find better way to access cards you want. KINGDOM_7 is only the smithy card for this kingdom.
            buy(Supply.KINGDOM_7);
            numSmithies++;
        } else if (availableCoins >= 3) {
            //System.out.println("Buys Silver");
            buy(Supply.SILVER);
        }
    }

    public void reset() {
        super.reset();

        numSmithies = 0;
    }



    @Override
    public boolean react() {
        return false;
    }

    @Override
    public void onCellar(boolean[] discardDecisions) {

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

    @Override
    public void onNotify(Player activePlayer, PlayerEvent event, Card card) {

    }
}

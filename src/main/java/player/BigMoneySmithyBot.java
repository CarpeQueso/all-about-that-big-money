package main.java.player;

import main.java.card.Card;
import main.java.core.Game;
import main.java.core.Supply;
import main.java.util.messaging.PlayerEvent;

/**
 * Created by jon on 1/31/15.
 */
public class BigMoneySmithyBot extends Player {

    private int numSmithies;

    private int smithyIndex;

    public BigMoneySmithyBot() {
        super();

        numSmithies = 0;
        smithyIndex = -1;
    }

    @Override
    public void setup() {
        for (int i = Supply.KINGDOM_0; i <= Supply.KINGDOM_9; i++) {
            if (supply.view(i).id() == Game.SMITHY_ID) {
                smithyIndex = i;
            }
        }
    }

    @Override
    public void onActionPhase() {
        int handIndex = this.findCardInHand(Game.SMITHY_ID);
        if (handIndex != -1) {
            play(handIndex);
        }
    }

    @Override
    public void onBuyPhase() {
        int availableCoins = this.availableCoins();

        if (availableCoins >= 8) {
            buy(Supply.PROVINCE);
        } else if (availableCoins >= 6) {
            buy(Supply.GOLD);
        } else if (availableCoins >= 4 && smithyIndex != -1 && numSmithies < 1) {
            buy(smithyIndex);
            numSmithies++;
        } else if (availableCoins >= 3) {
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

    @Override
    public void onNotify(Player activePlayer, PlayerEvent event, Card card) {

    }
}

package main.java.player;

import main.java.card.Card;
import main.java.core.Game;
import main.java.core.Supply;
import main.java.util.messaging.PlayerEvent;
import main.java.util.messaging.Receiver;

/**
 * Created by jon on 3/24/15.
 */
public class SimpleEngineBot extends Player implements Receiver {

    @Override
    public void setUp() {
        marketIndex = -1;
        smithyIndex = -1;
        villageIndex = -1;
        for (int i = Supply.KINGDOM_0; i <= Supply.KINGDOM_9; i++) {
            int cardID = supply.view(i).id();
            switch(cardID) {
                case Game.MARKET_ID: marketIndex = i; break;
                case Game.SMITHY_ID: smithyIndex = i; break;
                case Game.VILLAGE_ID: villageIndex = i; break;
            }
        }
    }

    @Override
    public void onActionPhase() {
        while (this.getAvailableActions() > 0 && this.handContainsType(Card.TYPE_ACTION)) {
            int handIndex = -1;
            if ((handIndex = this.findCardInHand(Game.VILLAGE_ID)) != -1) {
                this.play(handIndex);
            } else if ((handIndex = this.findCardInHand(Game.MARKET_ID)) != -1) {
                this.play(handIndex);
            } else if ((handIndex = this.findCardInHand(Game.SMITHY_ID)) != -1) {
                this.play(handIndex);
            }
        }
    }

    @Override
    public void onBuyPhase() {
        while (this.getAvailableBuys() > 0) {
            int availableCoins = this.getAvailableCoins();
            if (availableCoins >= 8) {
                this.buy(Supply.PROVINCE);
            } else if (availableCoins >= 6) {
                this.buy(Supply.GOLD);
            } else if (availableCoins >= 5 && marketIndex != -1) { // index = -1 indicates the card is not in the game
                this.buy(marketIndex);
            } else if (availableCoins >= 4 && smithyIndex != -1) {
                this.buy(smithyIndex);
            } else if (availableCoins >= 3 && villageIndex != -1) {
                this.buy(villageIndex);
            } else {
                // I don't want any cards
                break;
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
    public boolean onChancellor() {
        return false;
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

    private int villageIndex;

    private int smithyIndex;

    private int marketIndex;
}

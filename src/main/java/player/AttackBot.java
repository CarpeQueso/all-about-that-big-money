package main.java.player;

import main.java.card.Card;
import main.java.util.messaging.PlayerEvent;

/**
 * Created by jon on 3/30/15.
 */
public class AttackBot extends Player {

    @Override
    public void setup() {

    }

    @Override
    public void onActionPhase() {

    }

    @Override
    public void onBuyPhase() {

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
    public void onMilitiaAttack(int[] cardsToKeep) {

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

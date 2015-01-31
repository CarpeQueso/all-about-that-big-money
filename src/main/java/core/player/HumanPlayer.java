package main.java.core.player;

import main.java.card.Card;
import main.java.core.Supply;

/**
 * Created by jon on 1/27/15.
 */
public class HumanPlayer extends Player {

    public HumanPlayer(Supply supply) {
        //super(supply);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void onActionPhase() {

    }

    @Override
    public void onBuyPhase() {

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
}

package main.java.card.action;

import main.java.player.Player;

/**
 * Created by jon on 1/26/15.
 */
public class CouncilRoomAction implements Action {

    private Player[] players;

    public CouncilRoomAction(Player[] players) {
        this.players = players;
    }

    public void onPlay(Player actingPlayer) {
        actingPlayer.drawToHand(4);
        actingPlayer.addBuys(1);

        for (Player player : players) {
            //Todo TEST THIS CONDITION. Need to know if it fails.
            if (player != actingPlayer) {
                player.drawToHand();
            }
        }
    }
}

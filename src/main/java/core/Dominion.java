package main.java.core;

import main.java.player.BigMoneyBot;
import main.java.player.BigMoneySmithyBot;
import main.java.player.Player;
import main.java.player.SimpleEngineBot;

/**
 * Created by jon on 3/13/15.
 */
public class Dominion {



    public static void main(String[] args) {
        Player[] players = new Player[2]; // This is set at 2 for now since we're focusing on the 2-player game.
        players[0] = new BigMoneyBot();
        //players[0] = new BigMoneySmithyBot();
        players[1] = new SimpleEngineBot();

        Game game = new Game(players);
        int[] kingdomCardsToUse = { Game.CELLAR_ID, Game.MOAT_ID, Game.VILLAGE_ID, Game.WOODCUTTER_ID, Game.WORKSHOP_ID,
                Game.MILITIA_ID, Game.REMODEL_ID, Game.SMITHY_ID, Game.MARKET_ID, Game.MINE_ID };

        //System.out.println("The winner is: " + game.run(kingdomCardsToUse));

        double p0Wins = 0;
        double p1Wins = 0;
        int draws = 0;
        int totalGames = 10000;

        for (int i = 0; i < totalGames; i++) {
            int winner = game.run(kingdomCardsToUse);
            if (winner == 0) {
                p0Wins++;
            } else if (winner == 1) {
                p1Wins++;
            } else {
                p0Wins += 0.5;
                p1Wins += 0.5;
            }
        }

        System.out.println("P0: " + p0Wins);
        System.out.println("P1: " + p1Wins);
        //System.out.println("Draws: " + draws);*/


    }


}

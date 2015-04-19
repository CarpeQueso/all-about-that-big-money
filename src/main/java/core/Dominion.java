package main.java.core;

import main.java.player.*;

import java.util.Random;

/**
 * Created by jon on 3/13/15.
 */
public class Dominion {



    public static void main(String[] args) {
        Player[] players = new Player[2]; // This is set at 2 for now since we're focusing on the 2-player game.
        players[0] = new BigMoneyBot();
        //players[1] = new BigMoneySmithyBot();
        players[1] = new RandomBot();
        //players[1] = new SimpleEngineBot();
        //players[1] = new HumanPlayer(System.out);

        Random random = new Random();

        Game game = new Game(players);
        int[] kingdomCardsToUse = { Game.CELLAR_ID, Game.MOAT_ID, Game.VILLAGE_ID, Game.WOODCUTTER_ID, Game.WORKSHOP_ID,
                Game.MILITIA_ID, Game.REMODEL_ID, Game.SMITHY_ID, Game.MARKET_ID, Game.MINE_ID };

        chooseRandomKingdomCards(kingdomCardsToUse);

        //System.out.println("The winner is: " + game.run(kingdomCardsToUse));

        double p0Wins = 0;
        double p1Wins = 0;
        int totalGames = 100000;

        for (int i = 0; i < totalGames; i++) {
            chooseRandomKingdomCards(kingdomCardsToUse);
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
    }

    public static void chooseRandomKingdomCards(int[] kingdomCardsToUse) {
        Random random = new Random();
        // Randomization for card testing
        int[] allPossibleKingdomCards = new int[25];
        for (int i = 0; i < allPossibleKingdomCards.length; i++) {
            allPossibleKingdomCards[i] = i + 7;
        }
        // Shuffle (naive)
        for (int i = allPossibleKingdomCards.length - 1; i >= 0; i--) {
            int swapIndex = random.nextInt(i + 1);

            int value = allPossibleKingdomCards[swapIndex];
            allPossibleKingdomCards[swapIndex] = allPossibleKingdomCards[i];
            allPossibleKingdomCards[i] = value;
        }
        // Print (for verification)
        /*for (int i = 0; i < allPossibleKingdomCards.length; i++) {
            System.out.print(allPossibleKingdomCards[i] + " ");
        }
        System.out.println();*/

        for (int i = 0; i < 10; i++) {
            kingdomCardsToUse[i] = allPossibleKingdomCards[i];
        }
    }


}

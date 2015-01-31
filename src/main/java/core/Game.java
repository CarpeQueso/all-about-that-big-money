package main.java.core;

import main.java.card.Card;
import main.java.card.action.*;
import main.java.card.value.GardensValue;
import main.java.card.value.SimpleValue;
import main.java.core.player.BigMoneyBot;
import main.java.core.player.BigMoneySmithyBot;
import main.java.core.player.Player;

/**
 * Created by jon on 1/16/15.
 */
public class Game {

    private Player[] players;

    private Supply supply;

    private final int numPlayers;

    /*
     * Template cards!
     */
    // Treasure
    private Card copper;

    private Card silver;

    private Card gold;

    // Victory
    private Card curse;

    private Card estate;

    private Card duchy;

    private Card province;

    private Card gardens;

    // Action
    private Card cellar;

    private Card chapel;

    private Card moat;

    private Card chancellor;

    private Card village;

    private Card woodcutter;

    private Card workshop;

    private Card bureaucrat;

    private Card feast;

    private Card militia;

    private Card moneylender;

    private Card remodel;

    private Card smithy;

    private Card spy;

    private Card thief;

    private Card throneRoom;

    private Card councilRoom;

    private Card festival;

    private Card laboratory;

    private Card library;

    private Card market;

    private Card mine;

    private Card witch;

    private Card adventurer;


    public Game(Player p0, Player p1) {
        numPlayers = 2;
        this.players = new Player[numPlayers];
        players[0] = p0;
        players[1] = p1;

        supply = new Supply();
    }

    public Game(Player p0, Player p1, Player p2) {
        numPlayers = 3;
        this.players = new Player[numPlayers];
        players[0] = p0;
        players[1] = p1;
        players[2] = p2;

        supply = new Supply();
    }

    public Game(Player p0, Player p1, Player p2, Player p3) {
        numPlayers = 4;
        this.players = new Player[numPlayers];
        players[0] = p0;
        players[1] = p1;
        players[2] = p2;
        players[3] = p3;

        supply = new Supply();
    }

    public int run() {
        init();
        playGame();
        return determineWinner();
    }

    public void init() {
        initCards();
        addCardsToSupply();
        for (Player player : players) {
            player.setSupply(supply);
        }
        buildPlayerStartingDecks();
        allowExtendingClassSetUp();
    }

    private void initCards() {
        initTreasureCards();
        initVictoryCards();
        initActionCards();
    }

    //Todo decide if the next two methods need named constants
    private void initTreasureCards() {
        copper = Card.initializeTreasureCard("Copper", 0, new SimpleValue(1));
        silver = Card.initializeTreasureCard("Silver", 3, new SimpleValue(2));
        gold = Card.initializeTreasureCard("Gold", 6, new SimpleValue(3));
    }

    private void initVictoryCards() {
        curse = Card.initializeVictoryCard("Curse", 0, new SimpleValue(-1));
        estate = Card.initializeVictoryCard("Estate", 2, new SimpleValue(1));
        duchy = Card.initializeVictoryCard("Duchy", 5, new SimpleValue(3));
        province = Card.initializeVictoryCard("Province", 8, new SimpleValue(6));
        gardens = Card.initializeVictoryCard("Gardens", 4, new GardensValue());
    }

    private void initActionCards() {
        cellar = Card.initializeActionCard("Cellar", 2, new CellarAction());
        //chapel;
        moat = Card.initializeReactionCard("Moat", 2, new MoatAction());
        //chancellor;
        village = Card.initializeActionCard("Village", 3, new VillageAction());
        woodcutter = Card.initializeActionCard("Woodcutter", 3, new WoodcutterAction());
        workshop = Card.initializeActionCard("Workshop", 3, new WorkshopAction());
        //bureaucrat;
        //feast;
        militia = Card.initializeActionCard("Militia", 4, new MilitiaAction(players));
        //moneylender;
        remodel = Card.initializeActionCard("Remodel", 4, new RemodelAction());
        smithy = Card.initializeActionCard("Smithy", 4, new SmithyAction());
        //spy;
        //thief;
        //throneRoom;
        councilRoom = Card.initializeActionCard("Council Room", 5, new CouncilRoomAction(players));
        festival = Card.initializeActionCard("Festival", 5, new FestivalAction());
        laboratory = Card.initializeActionCard("Laboratory", 5, new LaboratoryAction());
        //library;
        market = Card.initializeActionCard("Market", 5, new MarketAction());
        mine = Card.initializeActionCard("Mine", 5, new MineAction());
        witch = Card.initializeActionCard("Witch", 5, new WitchAction(players, curse));
        adventurer = Card.initializeActionCard("Adventurer", 6, new AdventurerAction());
    }

    private void addCardsToSupply() {
        //Add standard cards to the kingdom
        //Todo Make these numbers named constants
        supply.add(Supply.COPPER, copper, 60);
        supply.add(Supply.SILVER, silver, 40);
        supply.add(Supply.GOLD, gold, 30);

        supply.add(Supply.CURSE, curse, numPlayers * 10 - 10); // Standard number of curses according to game rules.
        //Todo change number of these cards based on number of players
        supply.add(Supply.ESTATE, estate, 20);
        supply.add(Supply.DUCHY, duchy, 10);
        supply.add(Supply.PROVINCE, province, 10);

        //Todo determine a means by which to select random kingdom cards

        // This set is based on the "first game" kingdom cards recommended by the rulebook.
        // They may be later changed to be randomly determined.
        supply.add(Supply.KINGDOM_0, cellar, 10);
        supply.add(Supply.KINGDOM_1, market, 10);
        supply.add(Supply.KINGDOM_2, militia, 10);
        supply.add(Supply.KINGDOM_3, mine, 10);
        supply.add(Supply.KINGDOM_4, moat, 10);
        supply.add(Supply.KINGDOM_5, remodel, 10);
        supply.add(Supply.KINGDOM_6, smithy, 10);
        supply.add(Supply.KINGDOM_7, village, 10);
        supply.add(Supply.KINGDOM_8, woodcutter, 10);
        supply.add(Supply.KINGDOM_9, workshop, 10);
    }

    private void buildPlayerStartingDecks() {
        for (Player player : players) {
            // Give player 7 copper
            for (int i = 0; i < 7; i++) {
                player.discard(supply.take(Supply.COPPER));
            }
            // Give player 3 estates
            for (int i = 0; i < 3; i++) {
                player.discard(supply.take(Supply.ESTATE));
            }
            player.cleanUp();
        }
    }

    private void allowExtendingClassSetUp() {
        for (Player player : players) {
            player.setUp();
        }
    }

    public void playGame() {
        // Select starting player randomly
        int playerIndex = (int) Math.round(Math.random() * (numPlayers - 1));
        Player currentPlayer = players[playerIndex];

        while (!gameOver()) {
            //System.out.println("--- Player " + playerIndex + "\'s Turn ---");
            currentPlayer.takeTurn();
            playerIndex = (playerIndex + 1) % players.length;
            currentPlayer = players[playerIndex];
        }
    }

    public boolean gameOver() {
        int numEmptyPiles = supply.getNumEmptyPiles();
        if (numEmptyPiles == 0) {
            return false;
        }
        if (numEmptyPiles == 3) {
            return true;
        } else if (supply.getNumCardsRemaining(Supply.PROVINCE) == 0) {
            return true;
        }
        return false;
    }

    public int determineWinner() {
        //Todo implement having multiple winners (based on rules).
        int highestScore = Integer.MIN_VALUE;
        int winningPlayerIndex = 0;
        int winningPlayerTurns = Integer.MAX_VALUE;
        int currentPlayerScore;
        int currentPlayerTurns;

        for (int i = 0; i < players.length; i++) {
            players[i].moveAllCardsToDeck();
            currentPlayerScore = players[i].getTotalVictoryPointsInDeck();
            currentPlayerTurns = players[i].getNumTurnsTaken();
            if (currentPlayerScore > highestScore) {
                highestScore = currentPlayerScore;
                winningPlayerIndex = i;
                winningPlayerTurns = currentPlayerTurns;
            } else if (currentPlayerScore == highestScore) {
                if (currentPlayerTurns < winningPlayerTurns) {
                    winningPlayerIndex = i;
                    winningPlayerTurns = currentPlayerTurns;
                } else if (currentPlayerTurns == winningPlayerTurns) {
                    //Todo revert hacky test thing because it's only 2-player compatible. More robust tie system.
                    winningPlayerIndex = 2;
                }
                // Otherwise, do nothing
            }
        }

        return winningPlayerIndex;
    }

    public static void main(String[] args) {
        double p0Wins = 0;
        double p1Wins = 0;
        int draws = 0;
        int totalGames = 10000;

        for (int i = 0; i < totalGames; i++) {
            int winner = new Game(new BigMoneyBot(), new BigMoneySmithyBot()).run();
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
        //System.out.println("Draws: " + draws);
    }
}

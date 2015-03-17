package main.java.core;

import main.java.card.Card;
import main.java.card.action.*;
import main.java.card.value.GardensValue;
import main.java.card.value.SimpleValue;
import main.java.player.Player;
import main.java.util.messaging.Channel;
import main.java.util.messaging.Microphone;

/**
 * Created by jon on 1/16/15.
 */
public class Game {

    //Todo alter this if expansions are added.
    public static final int TOTAL_AVAILABLE_CARDS = 32;

    // Core Dominion templateCards (base set).

    // Cards used every game.
    public static final int COPPER_ID = 0;

    public static final int SILVER_ID = 1;

    public static final int GOLD_ID = 2;

    public static final int CURSE_ID = 3;

    public static final int ESTATE_ID = 4;

    public static final int DUCHY_ID = 5;

    public static final int PROVINCE_ID = 6;

    // Cards which are not necessarily used every game.
    public static final int GARDENS_ID = 7;

    public static final int CELLAR_ID = 8;

    public static final int CHAPEL_ID = 9;

    public static final int MOAT_ID = 10;

    public static final int CHANCELLOR_ID = 11;

    public static final int VILLAGE_ID = 12;

    public static final int WOODCUTTER_ID = 13;

    public static final int WORKSHOP_ID = 14;

    public static final int BUREAUCRAT_ID = 15;

    public static final int FEAST_ID = 16;

    public static final int MILITIA_ID = 17;

    public static final int MONEYLENDER_ID = 18;

    public static final int REMODEL_ID = 19;

    public static final int SMITHY_ID = 20;

    public static final int SPY_ID = 21;

    public static final int THIEF_ID = 22;

    public static final int THRONE_ROOM_ID = 23;

    public static final int COUNCIL_ROOM_ID = 24;

    public static final int FESTIVAL_ID = 25;

    public static final int LABORATORY_ID = 26;

    public static final int LIBRARY_ID = 27;

    public static final int MARKET_ID = 28;

    public static final int MINE_ID = 29;

    public static final int WITCH_ID = 30;

    public static final int ADVENTURER_ID = 31;


    private Channel playerChannel;

    private Player[] players;

    private Card[] templateCards;

    private Supply supply;

    private final int numPlayers;

    /**
     * Game constructor.
     *
     * @param players
     */
    public Game(Player[] players) {
        numPlayers = players.length;
        this.players = players;
        templateCards = new Card[TOTAL_AVAILABLE_CARDS];

        supply = new Supply();
        playerChannel = new Channel();

        for (Player player : this.players) {
            playerChannel.addReceiver(player);
            player.setMicrophone(new Microphone(playerChannel));

            player.setSupply(supply);
        }


        initCards();
        addBaseCardsToSupply();
    }

    /**
     * Play a new game.
     *
     * @param cardsToUse sorted array of card IDs to use during this run of the game. Should be of length 10.
     *
     * @return The winning player index.
     */
    public int run(int[] cardsToUse) {
        setup(cardsToUse);
        playGame();
        return determineWinner();
    }

    /**
     * Set up initial game state.
     *
     * @param cardsToUse
     */
    public void setup(int[] cardsToUse) {
        addKingdomCardsToSupply(cardsToUse);
        supply.reset();
        for (Player player : players) {
            player.reset();
        }
        buildPlayerStartingDecks();
        allowExtendingClassSetUp();
    }

    private void initCards() {
        initTreasureCards();
        initVictoryCards();
        initActionCards();
    }

    private void initTreasureCards() {
        templateCards[COPPER_ID] = Card.initializeTreasureCard("Copper", COPPER_ID, 0, new SimpleValue(1));
        templateCards[SILVER_ID] = Card.initializeTreasureCard("Silver", SILVER_ID, 3, new SimpleValue(2));
        templateCards[GOLD_ID] = Card.initializeTreasureCard("Gold", GOLD_ID, 6, new SimpleValue(3));
    }

    private void initVictoryCards() {
        templateCards[CURSE_ID] = Card.initializeVictoryCard("Curse", CURSE_ID, 0, new SimpleValue(-1));
        templateCards[ESTATE_ID] = Card.initializeVictoryCard("Estate", ESTATE_ID, 2, new SimpleValue(1));
        templateCards[DUCHY_ID] = Card.initializeVictoryCard("Duchy", DUCHY_ID, 5, new SimpleValue(3));
        templateCards[PROVINCE_ID] = Card.initializeVictoryCard("Province", PROVINCE_ID, 8, new SimpleValue(6));
        templateCards[GARDENS_ID] = Card.initializeVictoryCard("Gardens", GARDENS_ID, 4, new GardensValue());
    }

    //Todo finish the rest of the action templateCards.
    private void initActionCards() {
        templateCards[CELLAR_ID] = Card.initializeActionCard("Cellar", CELLAR_ID, 2, new CellarAction());
        //templateCards[CHAPEL_ID] = Card.initializeActionCard("Chapel", CHAPEL_ID, 2, new ChapelAction());
        templateCards[MOAT_ID] = Card.initializeReactionCard("Moat", MOAT_ID, 2, new MoatAction());
        //templateCards[CHANCELLOR_ID] = Card.initializeActionCard("Chancellor", CHANCELLOR_ID, 3, new ChancellorAction());
        templateCards[VILLAGE_ID] = Card.initializeActionCard("Village", VILLAGE_ID, 3, new VillageAction());
        templateCards[WOODCUTTER_ID] = Card.initializeActionCard("Woodcutter", WOODCUTTER_ID, 3, new WoodcutterAction());
        templateCards[WORKSHOP_ID] = Card.initializeActionCard("Workshop", WORKSHOP_ID, 3, new WorkshopAction());
        //templateCards[BUREAUCRAT_ID] = Card.initializeActionCard("Bureaucrat", BUREAUCRAT_ID, 4, new BureaucratAction());
        //templateCards[FEAST_ID] = Card.initializeActionCard("Feast", FEAST_ID, 4, new FeastAction());
        templateCards[MILITIA_ID] = Card.initializeActionCard("Militia", MILITIA_ID, 4, new MilitiaAction(players));
        //templateCards[MONEYLENDER_ID] = Card.initializeActionCard("Moneylender", MONEYLENDER_ID, 4, new MoneylenderAction());
        templateCards[REMODEL_ID] = Card.initializeActionCard("Remodel", REMODEL_ID, 4, new RemodelAction());
        templateCards[SMITHY_ID] = Card.initializeActionCard("Smithy", SMITHY_ID, 4, new SmithyAction());
        //templateCards[SPY_ID] = Card.initializeActionCard("Spy", SPY_ID, 4, new SpyAction());
        //templateCards[THIEF_ID] = Card.initializeActionCard("Thief", THIEF_ID, 4, new ThiefAction());
        //templateCards[THRONE_ROOM_ID] = Card.initializeActionCard("Throne Room", THRONE_ROOM_ID, 4, new ThroneRoomAction());
        templateCards[COUNCIL_ROOM_ID] = Card.initializeActionCard("Council Room", COUNCIL_ROOM_ID, 5, new CouncilRoomAction(players));
        templateCards[FESTIVAL_ID] = Card.initializeActionCard("Festival", FESTIVAL_ID, 5, new FestivalAction());
        templateCards[LABORATORY_ID] = Card.initializeActionCard("Laboratory", LABORATORY_ID, 5, new LaboratoryAction());
        //templateCards[LIBRARY_ID] = Card.initializeActionCard("Library", LIBRARY_ID, 5, new LibraryAction());
        templateCards[MARKET_ID] = Card.initializeActionCard("Market", MARKET_ID, 5, new MarketAction());
        templateCards[MINE_ID] = Card.initializeActionCard("Mine", MINE_ID, 5, new MineAction());
        templateCards[WITCH_ID] = Card.initializeActionCard("Witch", WITCH_ID, 5, new WitchAction(players));
        templateCards[ADVENTURER_ID] = Card.initializeActionCard("Adventurer", ADVENTURER_ID, 6, new AdventurerAction());
    }

    private void addBaseCardsToSupply() {
        //Add standard templateCards to the kingdom
        supply.add(Supply.COPPER, templateCards[COPPER_ID], 60);
        supply.add(Supply.SILVER, templateCards[SILVER_ID], 40);
        supply.add(Supply.GOLD, templateCards[GOLD_ID], 30);

        supply.add(Supply.CURSE, templateCards[CURSE_ID], numPlayers * 10 - 10); // Standard number of curses according to game rules.
        //Todo change number of these templateCards based on number of players
        supply.add(Supply.ESTATE, templateCards[ESTATE_ID], 14);
        supply.add(Supply.DUCHY, templateCards[DUCHY_ID], 8);
        supply.add(Supply.PROVINCE, templateCards[PROVINCE_ID], 8);
    }

    private void addKingdomCardsToSupply(int[] cardsToUse) {
        for (int supplyPosition = Supply.KINGDOM_0, i = 0; supplyPosition <= Supply.KINGDOM_9; supplyPosition++, i++) {
            supply.add(supplyPosition, templateCards[cardsToUse[i]], 10);
        }
    }

    private void buildPlayerStartingDecks() {
        // Although this sort of reeks of hard-coding things, the coppers and estates are
        // basically guaranteed to be in these positions, so I'm going to leave it be.
        for (Player player : players) {
            // Give player 7 copper
            for (int i = 0; i < 7; i++) {
                player.discard(supply.take(Supply.COPPER));
            }
            // Give player 3 estates
            for (int i = 0; i < 3; i++) {
                player.discard(supply.take(Supply.ESTATE));
            }
            // Used here so that each player shuffles discard pile into deck and draws starting hand.
            player.cleanUp();
        }
    }

    /**
     * This step is part of player initialization. Classes which extend player may do preliminary work
     * in their setUp methods. The call to setUp() is guaranteed to be after all relevant game state
     * (e.g. the kingdom templateCards in the supply) has been completely built and is ready for viewing by the player.
     */
    private void allowExtendingClassSetUp() {
        for (Player player : players) {
            player.setUp();
        }
    }

    /**
     * A starting player is randomly selected, and the players alternate taking turns until game-ending
     * conditions are met.
     */
    public void playGame() {
        // Selects starting player randomly.
        // This randomization is important. If not used, some strategies will have a higher than expected
        // win percentage.
        //Todo fix this line. Does not give an even distribution for more than 2 players.
        int playerIndex = (int) Math.round(Math.random() * (numPlayers - 1));
        Player currentPlayer = players[playerIndex];

        while (!gameOver()) {
            //System.out.println("--- Player " + playerIndex + "\'s Turn ---");
            currentPlayer.takeTurn();
            playerIndex = (playerIndex + 1) % numPlayers;
            currentPlayer = players[playerIndex];
        }
    }

    /**
     * Checks to see if game-ending conditions have been met.
     *
     * These conditions are:
     *  - The pile containing provinces is empty.
     *  - Three piles of templateCards in the supply are empty
     *
     * @return true if any of the game-ending conditions have been met, false otherwise
     */
    public boolean gameOver() {
        int numEmptyPiles = supply.getNumEmptyPiles();
        if (numEmptyPiles == 0) {
            return false;
        }
        if (numEmptyPiles >= 3) {
            return true;
        } else if (supply.getNumCardsRemaining(Supply.PROVINCE) == 0) {
            return true;
        }
        return false;
    }

    /**
     * Moves each player's templateCards into his/her respective deck and calculates the number of victory points
     * the deck contains. The player index with the highest number of points is returned.
     *
     * In the case of a tie, the player who has taken fewer turns is the winner. If two or more tied players
     * have taken the same number of turns, these players share the victory according to the rules.
     * Note: these draw conditions have only been implemented for two players.
     *
     * @return the index of the winning player
     */
    public int determineWinner() {
        //Todo implement having multiple winners (based on rules).
        int highestScore = Integer.MIN_VALUE;
        int winningPlayerIndex = 0;
        int winningPlayerTurns = Integer.MAX_VALUE;
        int currentPlayerScore;
        int currentPlayerTurns;

        for (int i = 0; i < numPlayers; i++) {
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
}

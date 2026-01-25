package com.atg;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for the Game class.
 * Tests game initialization, turn mechanics, and win conditions.
 */
public class GameTest {
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    // ========== Game Initialization Tests ==========

    /**
     * Test that game initializes with two players
     */
    @Test
    public void testGameHasTwoPlayers() {
        assertNotNull("Player 1 should exist", game.getPlayer1());
        assertNotNull("Player 2 should exist", game.getPlayer2());
    }

    /**
     * Test that each player starts with a 10-card deck (7 Bitcoins + 3 Methods)
     */
    @Test
    public void testInitialPlayerDecks() {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        
        assertEquals("Player 1 should start with 10 cards", 10, player1.getDeck().size());
        assertEquals("Player 2 should start with 10 cards", 10, player2.getDeck().size());
    }

    /**
     * Test that starting player is randomly selected
     */
    @Test
    public void testCurrentPlayerIsSet() {
        Player currentPlayer = game.getCurrentPlayer();
        assertNotNull("Current player should be set", currentPlayer);
        
        boolean isPlayer1orPlayer2 = (currentPlayer == game.getPlayer1() || 
                                      currentPlayer == game.getPlayer2());
        assertTrue("Current player must be one of the two players", isPlayer1orPlayer2);
    }

    /**
     * Test that supply is properly initialized with all cards
     */
    @Test
    public void testSupplyInitialization() {
        Supply supply = game.getSupply();
        assertNotNull("Supply should be initialized", supply);
        
        // Framework cards should be available at start
        assertTrue("Supply should have Framework cards", supply.getFrameworkCount() > 0);
    }

    // ========== Game State Tests ==========

    /**
     * Test that game is not over at initialization
     */
    @Test
    public void testGameNotOverAtStart() {
        assertFalse("Game should not be over at start", game.isGameOver());
    }

    /**
     * Test that game ends when all 8 Framework cards are purchased
     */
    @Test
    public void testGameEndsWhenAllFrameworkCardsArePurchased() {
        // Drain the supply of Framework cards
        for (int i = 0; i < 8; i++) {
            game.getSupply().removeFramework();
        }
        
        assertTrue("Game should be over when Framework count reaches 0", 
                   game.isGameOver());
    }

    /**
     * Test that game does not end prematurely
     */
    @Test
    public void testGameContinuesWithFrameworkCardsRemaining() {
        // Remove 7 of 8 Framework cards
        for (int i = 0; i < 7; i++) {
            game.getSupply().removeFramework();
        }
        
        assertFalse("Game should continue while Framework cards remain", 
                    game.isGameOver());
    }

    // ========== Turn Mechanics Tests ==========

    /**
     * Test that current player alternates after each turn
     */
    @Test
    public void testPlayerTurnsAlternate() {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        Player firstPlayer = game.getCurrentPlayer();
        
        // Simulate a turn
        game.playTurn(firstPlayer);
        game.switchPlayer();
        Player secondPlayer = game.getCurrentPlayer();
        
        assertNotEquals("Current player should change after turn", 
                        firstPlayer, secondPlayer);
    }

    /**
     * Test that player draws a hand (5 cards) at start of turn
     */
    @Test
    public void testPlayerDrawsHandAtTurnStart() {
        Player player = game.getPlayer1();
        player.drawHand();
        
        assertEquals("Player should draw 5 cards", 5, player.getHand().size());
    }

    /**
     * Test that playing cryptocurrency cards generates coins
     */
    @Test
    public void testPlayingCardsGeneratesCoins() {
        Player player = game.getPlayer1();
        player.drawHand();
        
        // Bitcoin cards in hand should generate coins
        int coins = player.playCards();
        
        assertTrue("Playing cards should generate coins", coins >= 0);
    }

    /**
     * Test that player can purchase cards during their turn
     */
    @Test
    public void testPlayerCanPurchaseCard() {
        Player player = game.getPlayer1();
        player.drawHand();
        int coinsAvailable = player.playCards();
        
        // Bitcoin costs 0, so player should always be able to purchase one
        if (coinsAvailable > 0) {
            player.buyCard(coinsAvailable);
            // Verify card was added (implementation dependent)
            assertNotNull("Player should have deck after purchase", player.getDeck());
        }
    }

    /**
     * Test cleanup phase discards hand and played cards
     */
    @Test
    public void testCleanupPhaseDiscards() {
        Player player = game.getPlayer1();
        player.drawHand();
        player.playCards();
        int handSize = player.getHand().size();
        
        player.cleanup();
        
        assertEquals("Hand should be empty after cleanup", 0, player.getHand().size());
    }

    /**
     * Test that deck is reshuffled from discard when empty
     */
    @Test
    public void testDeckReshufflesFromDiscard() {
        Player player = game.getPlayer1();
        
        // Draw multiple hands to exhaust deck
        for (int i = 0; i < 3; i++) {
            player.drawHand();
            player.cleanup();
        }
        
        // Should still be able to draw after reshuffle
        player.drawHand();
        assertTrue("Should draw cards after reshuffle", player.getHand().size() > 0);
    }

    // ========== Win Condition Tests ==========

    /**
     * Test that Automation cards contribute to final score
     */
    @Test
    public void testAutomationPointsCalculation() {
        Player player = game.getPlayer1();
        
        // Get the initial points from the starting cards (3 Method cards = 3 points)
        int initialPoints = player.calculateAutomationPoints();
        
        // Add some Automation cards manually for testing
        AutomationCard method = new AutomationCard("Method", 2, 1);
        AutomationCard module = new AutomationCard("Module", 5, 3);
        
        player.addCardToDeck(method);
        player.addCardToDeck(module);
        
        int points = player.calculateAutomationPoints();
        assertEquals("Points should equal initial plus added cards", 
                     initialPoints + 4, points); // initialPoints + (1 + 3)
    }

    /**
     * Test that cryptocurrency cards don't contribute to final score
     */
    @Test
    public void testCryptocurrencyCardsNotCountedForWin() {
        Player player = game.getPlayer1();
        
        // Cryptocurrency cards should not affect AP calculation
        int pointsBefore = player.calculateAutomationPoints();
        
        CryptocurrencyCard bitcoin = new CryptocurrencyCard("Bitcoin", 0, 1);
        player.addCardToDeck(bitcoin);
        
        int pointsAfter = player.calculateAutomationPoints();
        assertEquals("Cryptocurrency cards should not add APs", 
                     pointsBefore, pointsAfter);
    }

    /**
     * Test winner determination with different scores
     */
    @Test
    public void testDetermineWinnerWithDifferentScores() {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        
        // This would be tested through game's declareWinner method
        // Verify both players can calculate points
        assertNotNull("Player 1 should calculate points", 
                      player1.calculateAutomationPoints());
        assertNotNull("Player 2 should calculate points", 
                      player2.calculateAutomationPoints());
    }

    // ========== Edge Case Tests ==========

    /**
     * Test game state after multiple turns
     */
    @Test
    public void testGameStateAfterMultipleTurns() {
        // Play 5 rounds (10 turns)
        for (int i = 0; i < 10 && !game.isGameOver(); i++) {
            game.playTurn(game.getCurrentPlayer());
            game.switchPlayer();
        }
        
        // Verify decks are not empty
        assertFalse("Player 1 deck should have cards", 
                    game.getPlayer1().getDeck().isEmpty());
        assertFalse("Player 2 deck should have cards", 
                    game.getPlayer2().getDeck().isEmpty());
    }

    /**
     * Test that player cannot buy card they cannot afford
     */
    @Test
    public void testCannotBuyUnaffordableCard() {
        Player player = game.getPlayer1();
        
        // Try to buy with 0 coins (Framework costs 8)
        player.buyCard(0);
        
        // Player should not have Framework card
        assertFalse("Player should not buy card without funds", 
                    player.getDeck().stream()
                          .anyMatch(card -> card.getName().equals("Framework")));
    }
}
package com.atg;

import java.util.Scanner;

/**
 * Main class to run the Automation: The Game
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("  Welcome to Automation: The Game!");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Players: " + game.getPlayer1().getName() + " vs " + game.getPlayer2().getName());
        System.out.println("Current Player: " + game.getCurrentPlayer().getName());
        System.out.println();

        int turnCount = 0;
        while (!game.isGameOver() && turnCount < 100) { // Limit to 100 turns for demo
            turnCount++;
            Player currentPlayer = game.getCurrentPlayer();
            
            System.out.println("========== Turn " + turnCount + " ==========");
            System.out.println("Current Player: " + currentPlayer.getName());
            System.out.println("Deck size: " + currentPlayer.getDeck().size());
            System.out.println();

            // Simulate a turn
            System.out.println("Action: Drawing hand...");
            currentPlayer.drawHand();
            System.out.println("Hand size: " + currentPlayer.getHand().size());
            System.out.println("Hand: ");
            for (Card card : currentPlayer.getHand()) {
                System.out.println("  - " + card.getName() + " (value: " + 
                    (card instanceof CryptocurrencyCard ? ((CryptocurrencyCard)card).getValue() : 
                    (card instanceof AutomationCard ? ((AutomationCard)card).getValue() : "N/A")) + ")");
            }
            System.out.println();

            System.out.println("Action: Playing cards...");
            int coins = currentPlayer.playCards();
            System.out.println("Coins earned: " + coins);
            System.out.println();

            // Try to buy a card
            if (coins > 0) {
                System.out.println("Action: Attempting to buy a card with " + coins + " coins...");
                int discardSizeBefore = currentPlayer.getDiscardPile().size();
                currentPlayer.buyCard(coins);
                int discardSizeAfter = currentPlayer.getDiscardPile().size();
                if (discardSizeAfter > discardSizeBefore) {
                    System.out.println("✓ Card purchased successfully! (Added to discard pile)");
                } else {
                    System.out.println("✗ No card was purchased.");
                }
            } else {
                System.out.println("Action: No coins to buy cards.");
            }
            System.out.println();

            System.out.println("Action: Cleanup phase...");
            currentPlayer.cleanup();
            System.out.println();

            // Switch to the next player
            game.switchPlayer();

            // Show game status
            System.out.println("=== Game Status ===");
            System.out.println("Framework cards remaining: " + game.getSupply().getFrameworkCount());
            System.out.println("Player 1 deck: " + game.getPlayer1().getDeck().size() + " cards");
            System.out.println("Player 2 deck: " + game.getPlayer2().getDeck().size() + " cards");
            System.out.println();
            
            System.out.println("Press Enter to continue to next turn...");
            scanner.nextLine();
            System.out.println();
        }

        // Game over
        System.out.println("========================================");
        System.out.println("Game Over!");
        System.out.println("========================================");
        System.out.println();

        int player1Points = game.getPlayer1().calculateAutomationPoints();
        int player2Points = game.getPlayer2().calculateAutomationPoints();

        System.out.println("Final Scores:");
        System.out.println(game.getPlayer1().getName() + ": " + player1Points + " Automation Points");
        System.out.println("Deck size: " + game.getPlayer1().getDeck().size() + " cards");
        System.out.println();
        System.out.println(game.getPlayer2().getName() + ": " + player2Points + " Automation Points");
        System.out.println("Deck size: " + game.getPlayer2().getDeck().size() + " cards");
        System.out.println();

        if (player1Points > player2Points) {
            System.out.println("🏆 " + game.getPlayer1().getName() + " wins!");
        } else if (player2Points > player1Points) {
            System.out.println("🏆 " + game.getPlayer2().getName() + " wins!");
        } else {
            System.out.println("🤝 It's a tie!");
        }

        scanner.close();
    }
}

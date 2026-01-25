package com.atg;

/**
 * Automatic game runner - plays without user interaction
 */
public class MainAuto {
    public static void main(String[] args) {
        Game game = new Game();

        System.out.println("========================================");
        System.out.println("  Welcome to Automation: The Game!");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Players: " + game.getPlayer1().getName() + " vs " + game.getPlayer2().getName());
        System.out.println();

        int turnCount = 0;
        while (!game.isGameOver() && turnCount < 1000) { // Auto-play without limit
            turnCount++;
            Player currentPlayer = game.getCurrentPlayer();
            
            // Simulate a turn
            currentPlayer.drawHand();
            int coins = currentPlayer.playCards();
            currentPlayer.buyCard(coins);
            currentPlayer.cleanup();

            // Switch to the next player
            game.switchPlayer();

            // Print progress every 10 turns
            if (turnCount % 10 == 0) {
                System.out.println("Turn " + turnCount + " - Framework remaining: " + game.getSupply().getFrameworkCount() + 
                                   " | P1 deck: " + game.getPlayer1().getDeck().size() + 
                                   " | P2 deck: " + game.getPlayer2().getDeck().size());
            }
        }

        // Game over
        System.out.println();
        System.out.println("========================================");
        System.out.println("Game Over after " + turnCount + " turns!");
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
    }
}

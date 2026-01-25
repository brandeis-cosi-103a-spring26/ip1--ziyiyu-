package com.atg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Supply supply;

    public Game() {
        supply = new Supply();
        player1 = new Player("Player 1", supply);
        player2 = new Player("Player 2", supply);
        currentPlayer = new Random().nextBoolean() ? player1 : player2;
    }

    public void start() {
        while (!isGameOver()) {
            playTurn(currentPlayer);
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
        declareWinner();
    }

    public void playTurn(Player player) {
        player.drawHand();
        int coins = player.playCards();
        Card cardToBuy = supply.getAvailableCards().values().stream()
            .filter(card -> card.getCost() <= coins)
            .findFirst()
            .orElse(null);
        if (cardToBuy != null) {
            player.buyCard(cardToBuy, supply);
        }
        player.cleanup();
    }

    public boolean isGameOver() {
        return supply.getFrameworkCount() == 0;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private void declareWinner() {
        int player1Points = player1.calculateAutomationPoints();
        int player2Points = player2.calculateAutomationPoints();
        
        if (player1Points > player2Points) {
            System.out.println(player1.getName() + " wins with " + player1Points + " APs!");
        } else if (player2Points > player1Points) {
            System.out.println(player2.getName() + " wins with " + player2Points + " APs!");
        } else {
            System.out.println("It's a tie with " + player1Points + " APs each!");
        }
    }

    // Added missing methods to interact with Player and Supply
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Supply getSupply() {
        return supply;
    }
}
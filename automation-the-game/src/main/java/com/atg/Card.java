package com.atg;

/**
 * Abstract class representing a card in the game.
 */
public abstract class Card {
    private final String name;
    private final int cost;
    private int quantity;

    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.quantity = 0; // Initialize quantity to 0
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
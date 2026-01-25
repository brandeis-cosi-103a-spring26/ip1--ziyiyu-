package com.atg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {
    private List<Card> hand;
    private Deck deck;
    private List<Card> discardPile;
    private int cryptocoins;
    private final String name;
    private final Supply supply;

    public Player() {
        this.hand = new ArrayList<>();
        this.deck = new Deck();
        this.discardPile = new ArrayList<>();
        this.cryptocoins = 0;
        this.name = "";
        this.supply = null;
    }

    // Added constructor to accept player name and supply
    public Player(String name, Supply supply) {
        this.name = name;
        this.supply = supply;
        this.hand = new ArrayList<>();
        this.deck = new Deck();
        this.discardPile = new ArrayList<>();
        this.cryptocoins = 0;
        
        // Initialize player with 7 Bitcoin and 3 Method cards
        for (int i = 0; i < 7; i++) {
            deck.addCard(new CryptocurrencyCard("Bitcoin", 0, 1));
        }
        for (int i = 0; i < 3; i++) {
            deck.addCard(new AutomationCard("Method", 2, 1));
        }
        deck.shuffle();
    }

    public List<Card> getHand() {
        return hand;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Card> getDiscardPile() {
        return discardPile;
    }

    public int getCryptocoins() {
        return cryptocoins;
    }

    public String getName() {
        return name;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public void addCardToDiscardPile(Card card) {
        discardPile.add(card);
    }

    public void playCard(Card card) {
        if (hand.contains(card)) {
            hand.remove(card);
            if (card instanceof CryptocurrencyCard) {
                cryptocoins += ((CryptocurrencyCard) card).getValue();
            }
        }
    }

    public void buyCard(Card card, Supply supply) {
        if (supply.hasCard(card)) {
            supply.removeCard(card);
            // Create a copy of the card to add to discard pile
            Card boughtCard;
            if (card instanceof AutomationCard) {
                AutomationCard ac = (AutomationCard) card;
                boughtCard = new AutomationCard(ac.getName(), ac.getCost(), ac.getValue());
            } else if (card instanceof CryptocurrencyCard) {
                CryptocurrencyCard cc = (CryptocurrencyCard) card;
                boughtCard = new CryptocurrencyCard(cc.getName(), cc.getCost(), cc.getValue());
            } else {
                boughtCard = card;
            }
            discardPile.add(boughtCard);
        }
    }

    public void endTurn() {
        discardPile.addAll(hand);
        hand.clear();
        // Logic to draw new hand from deck would go here
    }

    // Added method to calculate Automation Points
    public int calculateAutomationPoints() {
        return deck.getCards().stream()
                   .filter(card -> card instanceof AutomationCard)
                   .mapToInt(card -> ((AutomationCard) card).getValue())
                   .sum();
    }

    public void drawHand() {
        // Logic to draw a hand of cards from the deck
        for (int i = 0; i < 5; i++) {
            Card card = deck.drawCard();
            if (card == null) {
                // If deck is empty, reshuffle from discard pile
                if (!discardPile.isEmpty()) {
                    deck.getCards().addAll(discardPile);
                    discardPile.clear();
                    deck.shuffle();
                    card = deck.drawCard();
                }
            }
            if (card != null) {
                hand.add(card);
            }
        }
    }

    public int playCards() {
        int coinsEarned = 0;
        for (Card card : new ArrayList<>(hand)) {
            if (card instanceof CryptocurrencyCard) {
                coinsEarned += ((CryptocurrencyCard) card).getValue();
            }
        }
        // Don't clear hand here - let cleanup() handle it
        return coinsEarned;
    }

    public void cleanup() {
        // Logic to move all cards from hand to discard pile
        discardPile.addAll(hand);
        hand.clear();
    }

    public void addCardToDeck(Card card) {
        deck.addCard(card);
    }

    public void buyCard(int coins) {
        // Try to buy the best card we can afford
        // Prioritize: Framework > Module > Ethereum > Method > Dogecoin > Bitcoin
        Card cardToBuy = null;
        
        if (supply == null) {
            System.out.println("ERROR: Supply is null!");
            return;
        }
        
        Map<String, Card> availableCards = supply.getAvailableCards();
        
        if (coins >= 8 && availableCards.containsKey("Framework") && availableCards.get("Framework").getQuantity() > 0) {
            cardToBuy = availableCards.get("Framework");
        } else if (coins >= 5 && availableCards.containsKey("Module") && availableCards.get("Module").getQuantity() > 0) {
            cardToBuy = availableCards.get("Module");
        } else if (coins >= 3 && availableCards.containsKey("Ethereum") && availableCards.get("Ethereum").getQuantity() > 0) {
            cardToBuy = availableCards.get("Ethereum");
        } else if (coins >= 2 && availableCards.containsKey("Method") && availableCards.get("Method").getQuantity() > 0) {
            cardToBuy = availableCards.get("Method");
        } else if (coins >= 6 && availableCards.containsKey("Dogecoin") && availableCards.get("Dogecoin").getQuantity() > 0) {
            cardToBuy = availableCards.get("Dogecoin");
        } else if (availableCards.containsKey("Bitcoin") && availableCards.get("Bitcoin").getQuantity() > 0) {
            cardToBuy = availableCards.get("Bitcoin");
        }
        
        if (cardToBuy != null && coins >= cardToBuy.getCost()) {
            buyCard(cardToBuy, supply);
        }
    }
}
package com.atg;

import java.util.HashMap;
import java.util.Map;

public class Supply {
    private Map<String, Card> cards;

    public Supply() {
        cards = new HashMap<>();
        initializeCards();
    }

    private void initializeCards() {
        // Initialize cards with quantities according to game rules
        AutomationCard method = new AutomationCard("Method", 2, 1);
        method.setQuantity(14);
        cards.put("Method", method);
        
        AutomationCard module = new AutomationCard("Module", 5, 3);
        module.setQuantity(8);
        cards.put("Module", module);
        
        AutomationCard framework = new AutomationCard("Framework", 8, 6);
        framework.setQuantity(8);
        cards.put("Framework", framework);
        
        CryptocurrencyCard bitcoin = new CryptocurrencyCard("Bitcoin", 0, 1);
        bitcoin.setQuantity(60);
        cards.put("Bitcoin", bitcoin);
        
        CryptocurrencyCard ethereum = new CryptocurrencyCard("Ethereum", 3, 2);
        ethereum.setQuantity(40);
        cards.put("Ethereum", ethereum);
        
        CryptocurrencyCard dogecoin = new CryptocurrencyCard("Dogecoin", 6, 3);
        dogecoin.setQuantity(30);
        cards.put("Dogecoin", dogecoin);
    }

    public Card buyCard(String cardName) {
        Card card = cards.get(cardName);
        if (card != null && card.getQuantity() > 0) {
            card.decreaseQuantity();
            return card;
        }
        return null;
    }

    public Map<String, Card> getAvailableCards() {
        return cards;
    }

    // Added method to get the count of Framework cards
    public int getFrameworkCount() {
        Card frameworkCard = cards.get("Framework");
        return frameworkCard != null ? frameworkCard.getQuantity() : 0;
    }

    public boolean hasCard(Card card) {
        return cards.containsKey(card.getName()) && cards.get(card.getName()).getQuantity() > 0;
    }

    public void removeCard(Card card) {
        if (hasCard(card)) {
            cards.get(card.getName()).decreaseQuantity();
        }
    }

    public void removeFramework() {
        Card frameworkCard = cards.get("Framework");
        if (frameworkCard != null && frameworkCard.getQuantity() > 0) {
            frameworkCard.decreaseQuantity();
        }
    }
}
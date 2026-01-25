package com.atg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private int currentCardIndex;

    public Deck() {
        this.cards = new ArrayList<>();
        this.currentCardIndex = 0;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void shuffle() {
        Collections.shuffle(cards);
        currentCardIndex = 0;
    }

    public Card drawCard() {
        if (currentCardIndex < cards.size()) {
            return cards.get(currentCardIndex++);
        }
        return null; // No more cards to draw
    }

    public void discardAll(List<Card> discardPile) {
        discardPile.addAll(cards);
        cards.clear();
        currentCardIndex = 0;
    }

    public boolean isEmpty() {
        return currentCardIndex >= cards.size();
    }

    // Added method to get all cards in the deck
    public List<Card> getCards() {
        return cards;
    }

    public int size() {
        return cards.size();
    }

    public java.util.stream.Stream<Card> stream() {
        return cards.stream();
    }
}
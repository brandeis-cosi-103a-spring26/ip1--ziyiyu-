# Automation: The Game (ATG)

## Overview
Automation: The Game (ATG) is a deck-building card game inspired by the popular game Dominion. In ATG, players aim to construct a deck of cards that maximizes their Automation Points (APs) by purchasing cards from a shared supply.

## Game Mechanics
- Each player has their own deck of cards.
- Players can add cards to their deck by purchasing them from a shared supply.
- Cards can provide Automation Points (APs) or cryptocurrency for purchasing power.
- The game ends when all Framework cards have been purchased, and the player with the most APs wins.

## Cards
### Automation Cards
- **Method**: Cost: 2, Value: 1 (x14)
- **Module**: Cost: 5, Value: 3 (x8)
- **Framework**: Cost: 8, Value: 6 (x8)

### Cryptocurrency Cards
- **Bitcoin**: Cost: 0, Value: 1 (x60)
- **Ethereum**: Cost: 3, Value: 2 (x40)
- **Dogecoin**: Cost: 6, Value: 3 (x30)

## Setup
- The game supports 2 players, both automated.
- Each player starts with a deck of 7 Bitcoins and 3 Methods.
- Players shuffle their decks and draw 5 cards for their initial hand.
- The starting player is chosen randomly.

## Turn Phases
1. **Buy Phase**: Players play cryptocurrency cards to gain purchasing power and can buy one card.
2. **Cleanup Phase**: Players discard their hand and played cards, then draw a new hand from their deck.

## Project Structure
- **src/main/java/com/atg/Game.java**: Main game logic, including initialization and turn management.
- **src/main/java/com/atg/Player.java**: Defines the player class, managing hands, decks, and purchasing.
- **src/main/java/com/atg/Card.java**: Base class for cards, defining common properties.
- **src/main/java/com/atg/AutomationCard.java**: Class for automation cards, extending Card.
- **src/main/java/com/atg/CryptocurrencyCard.java**: Class for cryptocurrency cards, extending Card.
- **src/main/java/com/atg/Deck.java**: Manages the player's deck, including shuffling and drawing.
- **src/main/java/com/atg/Supply.java**: Manages the supply of cards available for purchase.
- **src/test/java/com/atg/GameTest.java**: Unit tests for the game logic.
- **pom.xml**: Maven configuration file for project dependencies and build settings.

## Running the Game
To run the game, ensure you have Maven installed. Use the following command to build and run the project:

```bash
mvn clean install
```

After building, you can run the game using your preferred method for executing Java applications. Enjoy playing Automation: The Game!
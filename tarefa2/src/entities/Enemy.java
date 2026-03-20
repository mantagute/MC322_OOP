package entities;
import java.util.Random;

import cards.Card;
import deck.DiscardPile;

public abstract class Enemy extends Entity {

    private static final int MAX_PLANNED_CARDS = 5;
    private Random rand = new Random();
    protected Card[] enemyStrategy = new Card[MAX_PLANNED_CARDS];



    public Enemy(String name, int health, int energy) {
        super(name, health, energy);
    }

    private void defineEnemyStrategy() {
        for (int index = 0; index < MAX_PLANNED_CARDS; index++) {
            enemyStrategy[index] = null;
        }
        int totalCardsDefined = 0;
        int auxcurrentEnergy = getEnergy();
        boolean[] selectedCards = new boolean[getHandSize()];
        while (totalCardsDefined < MAX_PLANNED_CARDS) {
            boolean anyAvailable = false;
            for (int i = 0; i < getHandSize(); i++) {
                if (!selectedCards[i] && getCardFromHand(i).getEnergyCost() <= auxcurrentEnergy) {
                    anyAvailable = true;
                    break;
                }
            }
            if (!anyAvailable) break;
            int index = rand.nextInt(getHandSize());
            Card card = getCardFromHand(index);
            if (!selectedCards[index] && card.getEnergyCost() <= auxcurrentEnergy) {
                enemyStrategy[totalCardsDefined] = card;
                totalCardsDefined++;
                auxcurrentEnergy -= card.getEnergyCost();
                selectedCards[index] = true;
            }
        }
    }

    public abstract String announceEnemyStrategy();

    public String prepareForBattle() {
        defineEnemyStrategy();
        return announceEnemyStrategy();
    }

    public void executeEnemyStrategy(Entity target, DiscardPile discardPile) {
        for (Card card : enemyStrategy) {
            int index = getCardIndex(card);
            if (card != null && index != -1)  {
                useCard(getCardIndex(card), target, discardPile);
            }
                
        }
    }

}

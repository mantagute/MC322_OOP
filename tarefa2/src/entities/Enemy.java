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
        for (int index = 0 ; index < MAX_PLANNED_CARDS ; index++) {
            enemyStrategy[index] = null;
        }
        int totalCardsDefined = 0;
        int auxcurrentEnergy = getEnergy();
        while (getHandSize() > 0 && totalCardsDefined < MAX_PLANNED_CARDS && super.hasEnoughEnergyForAnyCard(auxcurrentEnergy)){
            int index = rand.nextInt(getHandSize()); 
            if (getCardFromHand(index).getEnergyCost() <= auxcurrentEnergy) {
                enemyStrategy[totalCardsDefined] = getCardFromHand(index);
                totalCardsDefined++;
                auxcurrentEnergy = auxcurrentEnergy - getCardFromHand(index).getEnergyCost();
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

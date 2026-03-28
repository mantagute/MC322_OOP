package entities;
import java.util.Random;

import observer.Publisher;
import cards.Card;
import deck.BuyPile;
import deck.DiscardPile;

public abstract class Enemy extends Entity {

    private static final int MAX_PLANNED_CARDS = 5;
    private Random rand = new Random();
    protected Card[] enemyStrategy = new Card[MAX_PLANNED_CARDS];
    protected BuyPile buyPile = new  BuyPile();
    protected DiscardPile discardPile = new DiscardPile();
    protected Publisher publisher;


    public Enemy(String name, double health, int energy, Publisher publisher) {
        super(name, health, energy);
        this.publisher = publisher;
        initializeDeck();
    }

    protected abstract void initializeDeck();

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

    public void executeEnemyStrategy(Entity target) {
        for (Card card : enemyStrategy) {
            if (card != null){
                int index = getCardIndex(card);
                if (index != -1)  {
                    useCard(getCardIndex(card), target, discardPile);
                }
            }    
        }
    }

    public void newTurn() {
        newTurn(buyPile, discardPile);
    }

}

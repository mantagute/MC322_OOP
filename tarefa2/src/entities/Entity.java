package entities;

import cards.Card;
import deck.BuyPile;
import deck.DiscardPile;
import deck.Hand;

public abstract class Entity {
    private String name;
    private int health;
    private int currentShield;
    private int currentEnergy;
    private int maxEnergy;
    private Hand hand;

    public Entity(String name, int health, int energy) {
        this.name = name;
        this.health = health;
        this.currentEnergy = energy;
        this.maxEnergy = energy;
        this.currentShield = 0;
        this.hand = new Hand();
    }

    public void useCard(int index, Entity target, DiscardPile discardPile) {
        Card cardToUse = hand.getCard(index);
        if (currentEnergy >= cardToUse.getEnergyCost()) {
            currentEnergy = currentEnergy - cardToUse.getEnergyCost();
            cardToUse.useCard(this, target);
            hand.extractCard(index);
            discardPile.push(cardToUse);

        }
    }

    public void receiveDamage(int damage) {
        if (currentShield > damage) {
            currentShield = currentShield - damage;
        } else {
            health = health - (damage - currentShield);
            currentShield = 0;
        }
    }

    public void receiveShield(int shield) {
        currentShield = currentShield + shield;
    }

    public boolean hasEnoughEnergyForAnyCard(int energy) {
        return energy >= hand.getMinimumEnergyCost();
    }
    
    public boolean hasEnoughEnergyForAnyCard() {
        return hasEnoughEnergyForAnyCard(getEnergy());
    }

    public void newTurn(BuyPile buyPile, DiscardPile discardPile) {
        currentEnergy = maxEnergy;
        currentShield = 0;
        hand.moveAllCardsTo(discardPile);
        while (getHandSize() < Hand.MAX_HAND_SIZE){
            Card drawnCard = buyPile.drawCard(discardPile);
            if (drawnCard != null) {
                hand.push(drawnCard);
            }
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHandSize() {
        return hand.getSize();
    }

    public Card getCardFromHand(int index) {
        return hand.getCard(index);
    }

    public int getHealth() {
        return health;
    }

    protected int getCardIndex(Card carta) {
        for (int i = 0; i < getHandSize(); i++) {
            if (getCardFromHand(i) == carta) {
                return i;
            }
        }
        return -1;
    }

    public int getShield() {
        return currentShield;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return currentEnergy;
    }
}

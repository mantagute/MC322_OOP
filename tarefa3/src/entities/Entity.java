package entities;

import cards.Card;
import deck.BuyPile;
import deck.DiscardPile;
import deck.Hand;
import effects.Effect;
import effects.Effect.EffectType;
import effects.Poison;
import effects.Strength;
import observer.Publisher;
import java.util.StringJoiner;

import java.util.ArrayList;

public abstract class Entity {
    private String name;
    private int health;
    private int currentShield;
    private int currentEnergy;
    private int maxEnergy;
    private Hand hand;
    private ArrayList<Effect> effects = new ArrayList<>();

    public Entity(String name, int health, int energy) {
        this.name = name;
        this.health = health;
        this.currentEnergy = energy;
        this.maxEnergy = energy;
        this.currentShield = 0;
        this.hand = new Hand();
    }

    public void applyEffect(EffectType type, int balance, Publisher publisher) {
        for (Effect effect : effects) {
            if (effect.getType() == type) {
                effect.addBalance(balance);
                return;
            }
        }
        
        Effect newEffect;
        switch (type) {
            case POISON:
                newEffect = new Poison(this, balance, publisher);
                break;
            case STRENGTH:
                newEffect = new Strength(this, balance, publisher);
                break;
            default:
                return;
        }
        effects.add(newEffect);
    }

    public int applyEffectMultiplier(int baseValue) {
        for (Effect effect : effects) {
            if (effect.getType() == Effect.EffectType.STRENGTH) {
                return baseValue * effect.getBalance();
            }
        }
        return baseValue;
    }

    public void manageEffects() {
        effects.removeIf(effect -> effect.getBalance() <= 0);
    }

    public String getEffectString() {
        if (effects.isEmpty()) {
            return "Sem efeitos ativos";
        }
        StringJoiner joiner = new StringJoiner(" | ");
        for (Effect effect : effects) {
            joiner.add(effect.getString());
        }
        return joiner.toString();
    }

    public void useCard(int index, Entity target, DiscardPile discardPile) {
        Card cardToUse = hand.getCard(index);
        if (currentEnergy >= cardToUse.getEnergyCost()) {
            currentEnergy = currentEnergy - cardToUse.getEnergyCost();
            cardToUse.useCard(this, target);
            System.out.println(getName() + " usou " + cardToUse.getName() + "! " + cardToUse.getDescription());
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

    public void resetShield() {
        currentShield = 0;
    }

    public void newTurn(BuyPile buyPile, DiscardPile discardPile) {
        currentEnergy = maxEnergy;
        manageEffects();
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

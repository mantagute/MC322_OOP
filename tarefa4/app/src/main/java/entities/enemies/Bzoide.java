package entities.enemies;

import cards.Card;
import cards.ShieldCard;
import entities.Enemy; 
import observer.Publisher;
import gameOrchestrator.Data;

public class Bzoide extends Enemy {
    
    public Bzoide(String name, double health, int energy, Publisher publisher) {
        super(name, health, energy, publisher);
    }

    public String announceEnemyStrategy(){
        double totalShield = 0;
        for (Card card : getEnemyStrategy()) {
            if (card instanceof ShieldCard) {
                totalShield = totalShield + card.getEffectValue();
            }
        }
        return "O bzoide, " + getName() + ", planeja usar " + totalShield + " de escudo no próximo turno!";
    }

    public void initializeDeck() {
        for (Card card : Data.bzoideDamageCards) addCardToBuypile(card);
        for (Card card : Data.bzoideShieldCards) addCardToBuypile(card);
        for (Card card : Data.bzoideEffectCards(publisher)) addCardToBuypile(card);
        shuffleBuyPile();
    }
}


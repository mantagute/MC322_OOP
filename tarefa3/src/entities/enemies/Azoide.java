package entities.enemies;

import cards.Card;
import cards.DamageCard;
import entities.Enemy; 
import gameOrchestrator.Data;
import observer.Publisher;

public class Azoide extends Enemy {

    
    public Azoide(String name, double health, int energy, Publisher publisher) {
        super(name, health, energy, publisher);
    }

    public String announceEnemyStrategy(){
        double totalDamage = 0;
        for (Card card : enemyStrategy) {
            if (card instanceof DamageCard) {
                totalDamage = totalDamage + card.getEffectValue();
            }
        }
        return "O azoide, " + getName() + ", planeja causar " + totalDamage + " de dano no próximo turno!";
    }

    public void initializeDeck() {
        Data.fillPile(buyPile, Data.azoideDamageCards);
        Data.fillPile(buyPile, Data.azoideShieldCards);
        Data.fillPile(buyPile, Data.azoideEffectCards(publisher));
        buyPile.shuffle();
    }
}

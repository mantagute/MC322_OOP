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
        for (Card card : getEnemyStrategy()) {
            if (card instanceof DamageCard) {
                totalDamage = totalDamage + card.getEffectValue();
            }
        }
        return "O azoide, " + getName() + ", planeja causar " + totalDamage + " de dano no próximo turno!";
    }

    public void initializeDeck() {
        for (Card card : Data.azoideDamageCards) addCardToBuypile(card);
        for (Card card : Data.azoideShieldCards) addCardToBuypile(card);
        for (Card card : Data.azoideEffectCards(publisher)) addCardToBuypile(card);
        shuffleBuyPile();
    }
}

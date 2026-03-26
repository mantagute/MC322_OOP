package entities.enemies;

import cards.Card;
import cards.ShieldCard;
import entities.Enemy; 

public class Bzoide extends Enemy {
    
    public Bzoide(String name, int health, int energy) {
        super(name, health, energy);
    }

    public String announceEnemyStrategy(){
        int totalShield = 0;
        for (Card card : enemyStrategy) {
            if (card instanceof ShieldCard) {
                totalShield = totalShield + card.getEffectValue();
            }
        }
        return "O bzoide, " + getName() + ", planeja usar " + totalShield + " de escudo no próximo turno!";
    }
}


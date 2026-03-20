package entities.enemies;

import cards.Card;
import cards.DamageCard;
import entities.Enemy; 

public class Azoide extends Enemy {
    
    public Azoide(String name, int health, int energy) {
        super(name, health, energy);
    }

    public String announceEnemyStrategy(){
        int totalDamage = 0;
        for (Card card : enemyStrategy) {
            if (card instanceof DamageCard) {
                totalDamage = totalDamage + card.getEffectValue();
            }
        }
        return "O azoide, " + getName() + ", planeja causar " + totalDamage + " de dano no próximo turno!";
    }
}

package cards;

import entities.Entity;

public class DamageCard extends Card {
    
    private int damage;

    public DamageCard(String name, int cost, int damage, String description) {
        super(name, cost, description);
        this.damage = damage;
    }

    public void useCard(Entity user, Entity target) {
        target.receiveDamage(this.damage);
    }

    public int getEffectValue() {
        return damage;
    }
}

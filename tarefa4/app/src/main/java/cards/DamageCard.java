package cards;

import entities.Entity;

public class DamageCard extends Card {
    
    private double damage;

    public DamageCard(String name, int cost, double damage, String description, boolean multiTarget) {
        super(name, cost, description, multiTarget);
        this.damage = damage;
    }

    public void useCard(Entity user, Entity target) {
        target.receiveDamage(user.applyEffectMultiplier(this.damage));
    }

    public double getEffectValue() {
        return damage;
    }

    public String getDetails() {
        return " (Dano: " + this.getEffectValue() + ")";
    }
}

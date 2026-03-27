package cards;

import entities.Entity;

public class ShieldCard extends Card {
    
    private double shield;

    public ShieldCard(String name, int energyCost, double shield, String description) {
        super(name, energyCost, description);
        this.shield = shield;
    }

    public void useCard(Entity user, Entity target) {
        user.receiveShield(user.applyEffectMultiplier(this.shield));
    }

    public double getEffectValue() {
        return shield;
    }

    public String getDetails() {
        return " (Escudo: " + this.getEffectValue() + ")";
    }

}
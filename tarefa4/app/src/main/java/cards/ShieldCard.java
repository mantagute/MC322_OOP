package cards;

import entities.Entity;

public class ShieldCard extends Card {
    
    private double shield;

    public ShieldCard(String name, int energyCost, double shield, String description, boolean multiTarget) {
        super(name, energyCost, description, multiTarget);
        this.shield = shield;
    }

    public void useCard(Entity user, Entity target) {
        user.receiveShield(user.applyEffectMultiplier(this.shield));
    }

    @Override
    public boolean isSelfTarget() {
        return true;
    }

    public double getEffectValue() {
        return shield;
    }

    public String getDetails() {
        return " (Escudo: " + this.getEffectValue() + ")";
    }

}
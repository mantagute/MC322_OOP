package cards;

import entities.Entity;

public class ShieldCard extends Card {
    
    private int shield;

    public ShieldCard(String name, int energyCost, int shield, String description) {
        super(name, energyCost, description);
        this.shield = shield;
    }

    public void useCard(Entity user, Entity target) {
        user.receiveShield(this.shield);
    }

    public int getEffectValue() {
        return shield;
    }

}
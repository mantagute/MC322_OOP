package cards;

import effects.Effect;
import entities.Entity;

public class EffectCard extends Card {

    private final Effect effect;

    public EffectCard(String name, int energyCost, String description, Effect effect) {
        super(name, energyCost, description);
        this.effect = effect;
    }

    public int getEffectValue() {
        return effect.getBalance();
    }

    public void useCard(Entity user, Entity enemy) {
        user.applyEffect(effect);
    }

}

package cards;

import effects.Effect.EffectType;
import entities.Entity;
import observer.Publisher;

public class EffectCard extends Card {

    private EffectType effectType;
    private int balance;
    private Publisher publisher;
    private boolean selfTarget;

    public EffectCard(String name, int energyCost, String description, EffectType effectType, int balance, boolean selfTarget, Publisher publisher) {
        super(name, energyCost, description);
        this.effectType = effectType;
        this.balance = balance;
        this.selfTarget = selfTarget;
        this.publisher = publisher;
    }

    public int getEffectValue() {
        return balance;
    }

    public void useCard(Entity user, Entity target) {
        Entity character = selfTarget ? user : target;
        character.applyEffect(effectType, balance, publisher);
    }
}

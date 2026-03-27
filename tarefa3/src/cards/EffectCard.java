package cards;

import effects.Effect.EffectType;
import entities.Entity;
import observer.Publisher;

public class EffectCard extends Card {

    private EffectType effectType;
    private double balance;
    private Publisher publisher;
    private boolean selfTarget;

    public EffectCard(String name, int energyCost, String description, EffectType effectType, double balance, boolean selfTarget, Publisher publisher) {
        super(name, energyCost, description);
        this.effectType = effectType;
        this.balance = balance;
        this.selfTarget = selfTarget;
        this.publisher = publisher;
    }

    public double getEffectValue() {
        return balance;
    }

    public void useCard(Entity user, Entity target) {
        Entity character = selfTarget ? user : target;
        character.applyEffect(effectType, balance, publisher);
    }

    public String getDetails() {
        if (effectType == EffectType.POISON) {
            return " (Reduz o HP do inimigo na quantidade de acúmulos atuais associados ao efeito.)";
        } else if (effectType == EffectType.STRENGTH) {
            return " (Multiplica dano e escudo pela quantidade de acúmulos atuais associados ao efeito.)";
        }
        return "";
    }
}

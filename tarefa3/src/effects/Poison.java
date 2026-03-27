package effects;

import observer.Publisher;
import entities.Entity;

public class Poison extends Effect {

    public Poison(Entity character, int balance, Publisher publisher) {
        super("Poison",character, balance, publisher);
    }

    public void beNotified(String event, Entity user, Entity target) {
        if (event.equals("FIM_TURNO")) {
            character.receiveDamage(getBalance());
            reduceBalance(1);
        }
    }

    public Effect.EffectType getType() { 
        return Effect.EffectType.POISON; 
    }

}

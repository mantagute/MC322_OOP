package effects;

import entities.Entity;
import observer.Publisher;

public class Strength extends Effect {

    public Strength(Entity character, double balance, Publisher publisher) {
        super("Strength", character, balance, publisher);
    }
    
    public void beNotified(String event, Entity user, Entity target) {
        if (event.equals("FIM_TURNO") && getBalance() > 1 && user == character) {
            reduceBalance(0.25);
            if (getBalance() <= 1) {
                publisher.unsubscribe(this);
            }
        }
    }

    public boolean shouldExpire() {
        return getBalance() <= 1;
    }

    public Effect.EffectType getType() { 
        return Effect.EffectType.STRENGTH; 
    }
}
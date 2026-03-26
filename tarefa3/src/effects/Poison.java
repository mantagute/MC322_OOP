package effects;

import observer.Publisher;
import entities.Entity;

public class Poison extends Effect {

    public Poison(Entity character, int balance, Publisher publisher) {
        super("Poison",character, balance, publisher);
    }

    public void beNotified(String event) {
        if (event == "FIM_TURNO") {
            reduceBalance(1);
        }
    }
}

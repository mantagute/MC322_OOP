package effects;

import entities.Entity;
import observer.Publisher;

public class Strength extends Effect {

    public Strength(Entity character, int balance, Publisher publisher) {
        super("Strength", character, balance, publisher);
    }
    
    public void beNotified(String event) {
        if (event == "FIM_TURNO") {
            reduceBalance(1);
        }
    }
}
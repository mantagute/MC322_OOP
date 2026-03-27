package effects;

import observer.Subscriber;
import observer.Publisher;
import entities.Entity;

public abstract class Effect extends Subscriber{
    private String type;
    private int balance;
    protected Publisher publisher;
    protected Entity character;

    public enum EffectType {
        POISON, STRENGTH
    }

    public Effect(String type, Entity character, int balance, Publisher publisher) {
        this.type = type;
        this.character = character;
        this.balance = balance;
        this.publisher = publisher;
        publisher.subscribe(this);
    }

    protected void reduceBalance(int reduction) {
        balance = balance - reduction;
        if (balance <= 0) {
            publisher.unsubscribe(this);
        }
    }

    public void addBalance(int addition) {
        balance = balance + addition;
    }

    public abstract EffectType getType();

    public String getString() {
        return type + "-> " + balance + (balance == 1 ? "acúmulo." : "acúmulos." ) ;
    }


    public int getBalance() {
        return balance;
    }
}



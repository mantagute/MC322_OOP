package effects;

import observer.Subscriber;
import observer.Publisher;
import entities.Entity;

public abstract class Effect extends Subscriber{
    private String type;
    private Entity character;
    private int balance;
    protected Publisher publisher;


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

    public String getString() {
        return "Efeito do tipo" + type + ": aplicado por mais" + balance + " turnos." ;
    }


    public int getBalance() {
        return balance;
    }
}



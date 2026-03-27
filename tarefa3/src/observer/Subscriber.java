package observer;

import entities.Entity;

public abstract class Subscriber {
    public abstract void beNotified(String event, Entity user, Entity target);
}

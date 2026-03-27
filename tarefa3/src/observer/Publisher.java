package observer;

import java.util.ArrayList;
import java.util.List;
import entities.Entity;

public class Publisher {

    private List<Subscriber> subscribers = new ArrayList<>();


    public void subscribe(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notify(String Event, Entity user, Entity target) {
        List <Subscriber> currentSubscribers = new ArrayList<>(subscribers);
        for (Subscriber subscriber : currentSubscribers) {
            subscriber.beNotified(Event, user, target);
        }
    }
}

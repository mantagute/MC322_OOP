package events;

import java.util.Scanner;

import deck.BuyPile;
import deck.DiscardPile;
import entities.Hero;

public abstract class Event {
    
    public enum EventResult {
        CONTINUE,
        DEFEAT,
        QUIT
    }
    
    public abstract EventResult initializeEvent(Hero hero, BuyPile buyPile, DiscardPile discardPile, Scanner scanner);

    public abstract String getPreview();
}

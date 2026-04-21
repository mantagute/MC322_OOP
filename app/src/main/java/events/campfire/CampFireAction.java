package events.campfire;

import deck.DiscardPile;
import deck.BuyPile;
import entities.Hero;
import java.util.Scanner;


public interface CampFireAction {

    String getDescription();

    void execute(Hero hero, BuyPile buyPile, DiscardPile discardPile, Scanner scanner);
}

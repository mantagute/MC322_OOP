package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import deck.BuyPile;
import deck.DiscardPile;
import entities.Hero;
import events.campfire.CampFireAction;
import events.campfire.Rest;


public class CampFire extends Event{
        public EventResult initializeEvent(Hero hero, BuyPile buyPile, DiscardPile discardPile, Scanner scanner) {
            List<CampFireAction> actions = new ArrayList<>();
            actions.add(new Rest());

            //TODO escolha 

            if (choice <= actions.size()) {
                CampFireAction campFireActionChosen = actions.get(choice - 1);
                campFireActionChosen.execute(hero, buyPile, discardPile, scanner);
            }
            
            return EventResult.CONTINUE;
        }
}

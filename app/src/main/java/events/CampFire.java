package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import deck.BuyPile;
import deck.DiscardPile;
import entities.Hero;
import events.campfire.CampFireAction;
import events.campfire.Rest;
import events.campfire.UpgradeCard;
import gameOrchestrator.UserInterface;


public class CampFire extends Event{
        public EventResult initializeEvent(Hero hero, BuyPile buyPile, DiscardPile discardPile, Scanner scanner) {
            List<CampFireAction> actions = new ArrayList<>();
            actions.add(new Rest());
            actions.add(new UpgradeCard());

            UserInterface.printCampFireOptions(actions);
            UserInterface.printChoicePrompt();

            int choice = scanner.nextInt();

            if (choice >=1 && choice <= actions.size()) {
                CampFireAction campFireActionChosen = actions.get(choice - 1);
                campFireActionChosen.execute(hero, buyPile, discardPile, scanner);
            }
            
            return EventResult.CONTINUE;
        }

        public String getPreview() {
            return "🔥 Fogueira";
        }
}

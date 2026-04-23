package events;


import java.util.Scanner;
import java.util.Collections;
import java.util.List;

import deck.BuyPile;
import deck.DiscardPile;
import entities.Hero;
import events.choice.ChoiceOption;
import gameOrchestrator.GameUtils;
import gameOrchestrator.UserInterface;

public class Choice extends Event{

    private String Lore;
    private List<ChoiceOption> choiceOptions;

    public Choice(String Lore, List<ChoiceOption> choiceOptions) {
        this.Lore = Lore;
        this.choiceOptions = choiceOptions;
    }

    public EventResult initializeEvent(Hero hero, BuyPile buyPile, DiscardPile discardPile, Scanner scanner ) {
        
        Collections.shuffle(choiceOptions);
        UserInterface.printChoiceLore(Lore);
        UserInterface.printChoiceOptions(choiceOptions);
        UserInterface.printChoicePrompt();

        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= choiceOptions.size()) {
            choiceOptions.get(choice - 1).execute(hero, buyPile, discardPile, scanner);
            UserInterface.printChoiceFeedback(choiceOptions.get(choice - 1));
            GameUtils.Wait(2500);
        }
        return EventResult.CONTINUE;
    }
    
}

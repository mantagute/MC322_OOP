package events.choice;

import entities.Hero;

import java.util.Scanner;

import deck.BuyPile;
import deck.DiscardPile;

public class HealOption implements ChoiceOption{

    private String action;
    private String feedback;

    public HealOption(String action, String feedback) {
        this.action = action;
        this.feedback = feedback;
    }

    public String getAction() {
        return action;
    }

    public String getFeedback() {
        return feedback;
    }
    
    public String getEmoji(){
        return "❤️";
    }

    public void execute(Hero hero, BuyPile buyPile, DiscardPile discardPile, Scanner scanner) {
        hero.heal(hero.getMaxHealth() * 0.1);
    }
}

package events.choice;

import java.util.Scanner;

import entities.Hero;
import deck.BuyPile;
import deck.DiscardPile;

public interface ChoiceOption {

    public String getAction();

    public String getFeedback();

    public String getEmoji();

    public void execute(Hero hero, BuyPile buyPile, DiscardPile discardPile, Scanner scanner);
}

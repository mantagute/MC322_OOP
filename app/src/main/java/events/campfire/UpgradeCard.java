package events.campfire;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import deck.BuyPile;
import deck.DiscardPile;
import entities.Hero;
import events.campfire.CampFireAction;
import cards.Card;

public class UpgradeCard implements CampFireAction {

    public String getDescription() {
        return "Forjar: (Melhora os atributos de uma carta do seu baralho)";
    }

    public void execute(Hero hero, BuyPile buyPile, DiscardPile discardPile, Scanner scanner) {
        System.out.println("\n--- SUAS CARTAS ---");
        
        List<Card> allCards = new ArrayList<>();
        
        // TODO: pegar todas as cartas e colocar a decisao de qual carta melhorar
        
        if (choice <= allCards.size()) {
            Card CardChosen = allCards.get(choice - 1);
            
            // Assumindo que você criará (ou já tem) um método de upgrade na classe Card
            CardChosen.upgrade(); 
        }
    }
}

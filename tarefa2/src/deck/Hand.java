package deck;

import cards.Card;

public class Hand extends Pile {

    public static final int MAX_HAND_SIZE = 5;

    public Hand() {
        super(MAX_HAND_SIZE);
    }

    public int getMinimumEnergyCost() {
        if (getSize() == 0) {
            return Integer.MAX_VALUE;
        }
        Card[] cardsInHand = getCards();
        int minEnergyCost = Integer.MAX_VALUE;
        for (Card card : cardsInHand) {
            if (card.getEnergyCost() < minEnergyCost) {
                minEnergyCost = card.getEnergyCost();
            }
        }
        return minEnergyCost;
    }
}

public class Hand extends Pile {

    public static final int MAX_HAND_SIZE = 5;

    public Hand() {
        super(MAX_HAND_SIZE);
    }

    public int getMinimumEnergyCost() {
        Card[] cardsInHand = getCards();
        int minEnergyCost = 999999999;
        for (Card card : cardsInHand) {
            if (card.getEnergyCost() < minEnergyCost) {
                minEnergyCost = card.getEnergyCost();
            }
        }
        return minEnergyCost;
    }
}

public class Pile {
    private Card[] cards;
    private int size;

    public Pile(Card[] cards) {
        this.cards = cards;
        this.size = cards.length;
    }

    public int getMinimumEnergyCost() {
        int minEnergyCost = 999999999;
        for (Card card : cards) {
            if (card.getEnergyCost() < minEnergyCost) {
                minEnergyCost = card.getEnergyCost();
            }
        }
        return minEnergyCost;
    }

    public int getSize() {
        return size;
    }

    public Card getCard(int index) {
        if (index >= 0 && index < size) {
            return cards[index];
        }
        return null;
    }
}

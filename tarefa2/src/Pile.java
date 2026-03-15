public class Pile {
    private Card[] cards;
    private int size;

    public Pile(int deckSize) {
        this.cards = new Card[deckSize];
        this.size = 0;
    }

    public void push(Card card) {
        if (size < cards.length) {
            cards[size] = card;
            size++;
        }
    }

    public void pop() {
        if (size > 0) {
            cards[size - 1] = null;
            size--;
        }
    }

    public int getSize() {
        return size;
    }

    public Card extractCard(int index) {
        if (index >= 0 && index < size) {
            Card cardToBeExtracted = cards[index];
            for (int i = index; i < size - 1; i++) {
                cards[i] = cards[i + 1];
            }
            cards[size - 1] = null;
            size--;
            return cardToBeExtracted;
        }
        return null;
    }
    
    public Card getCard(int index) {
        if (index >= 0 && index < size) {
            return cards[index];
        }
        return null;
    }

    public Card[] getCards() {
        Card[] pileCards = new Card[size];
        for (int i = 0; i < size; i++) {
            pileCards[i] = cards[i];
        }
        return pileCards;
    }
}

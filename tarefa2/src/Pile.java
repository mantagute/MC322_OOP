public class Pile {
    private Card[] cards;
    private int size;

    public Pile(Card[] cards) {
        this.cards = cards;
        this.size = cards.length;
    }

    public void push(Card card) {
        if (size < cards.length) {
            cards[size] = card;
            size++;
        }
    }

    public void pop(Card[] cards) {
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
            Card card = cards[index];
            pop(cards);
            return card;
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
        return cards;
    }
}

public class Pile {
    private Card[] cards;
    private int size;
    protected static final int MAX_DECK_SIZE = 20;

    public Pile(int pileSize) {
        this.cards = new Card[pileSize];
        this.size = 0;
    }

    public void push(Card card) {
        if (size < cards.length) {
            cards[size] = card;
            size++;
        }
    }

    protected void swapCards(int index1, int index2) {
        if (index1 >=0 && index1 < size && index2 >= 0 && index2 < size) {
            Card auxCard = cards[index1];
            cards[index1] = cards[index2];
            cards[index2] = auxCard;
        } 
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

    public void moveAllCardsTo(Pile targetPile) {
        while (size > 0) {
            Card cardtomove = extractCard(getSize() - 1);
            targetPile.push(cardtomove);
        }
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

    public Card[] getCards() {
        Card[] pileCards = new Card[size];
        for (int i = 0; i < size; i++) {
            pileCards[i] = cards[i];
        }
        return pileCards;
    }
}

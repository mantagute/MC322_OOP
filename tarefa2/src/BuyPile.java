public class BuyPile extends Pile {

    public BuyPile(Card[] cards) {
        super(cards);
    }

    public Card drawCard() {
        if (getSize() > 0) {
            Card card = extractCard(getSize() - 1);
            return card;
        }
        return null;
    }

    public void renew(Pile discardPile) {
        for (Card card : discardPile.getCards()) {
            push(card);
        }
    }
}


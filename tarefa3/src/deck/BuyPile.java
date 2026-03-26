package deck;

import cards.Card;

public class BuyPile extends Pile {

    public BuyPile() {
        super(MAX_DECK_SIZE);
    }

    public Card drawCard(DiscardPile discardPile) {
        if (getSize() == 0 && discardPile.getSize() > 0) {
            renewBuyPile(discardPile);
        }
        if (getSize() > 0) {
            Card drawnCard = extractCard(getSize() - 1);
            return drawnCard;
        }
        return null;
    }

    private void renewBuyPile(DiscardPile discardPile) {
        discardPile.shuffle();
        discardPile.moveAllCardsTo(this);
    }
}


package deck;

import cards.DamageCard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PileTest {

    @Test
    public void InitiallizedEmpty() {
        DiscardPile pile = new DiscardPile();
        assertEquals(0, pile.getSize());
    }

    @Test
    public void sizeIsIncreased() {
        DiscardPile pile = new DiscardPile();
        DamageCard card = new DamageCard("nova carta de dano", 3, 10, null, false);
        pile.push(card);
        assertEquals(1, pile.getSize());
    }

    @Test
    public void sizeIsDecreasedFromCorrectCardExtracted() {
        DiscardPile pile = new DiscardPile();
        DamageCard card = new DamageCard("vou ser removida", 3, 10, null, false);
        pile.push(card);
        assertEquals(pile.extractCard(0), card);
        assertEquals(0, pile.getSize());
    }

    @Test
    public void extractsCardFromInvalidIndex() { 
        DiscardPile pile = new DiscardPile();
        assertNull(pile.extractCard(10));
    }

    @Test
    public void allCardsMovedToOtherPile() {
        Hand handpile = new Hand();
        DiscardPile discardPile = new DiscardPile();
        DamageCard card1 = new DamageCard("carta1", 3, 10, null, false);
        DamageCard card2 = new DamageCard("carta2", 3, 10, null, false);
        handpile.push(card1);
        handpile.push(card2);
        handpile.moveAllCardsTo(discardPile);
        assertEquals(0, handpile.getSize());
        assertEquals(2, discardPile.getSize());
    }
}

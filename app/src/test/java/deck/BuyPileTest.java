package deck;

import cards.DamageCard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BuyPileTest {
    @Test
    public void BuyPileisRenewed() {
        BuyPile buypile = new BuyPile();
        DiscardPile discardPile = new DiscardPile();
        DamageCard card1 = new DamageCard("carta1", 3, 10, null, false);
        DamageCard card2 = new DamageCard("card2", 3, 10, null, false);
        buypile.push(card1);
        buypile.push(card2);
        buypile.drawCard(discardPile);
        buypile.drawCard(discardPile);
        discardPile.push(card1);
        discardPile.push(card2);
        buypile.drawCard(discardPile);
        assertTrue(buypile.getSize() != 0);
    }

    @Test 
    public void drawCardFromEmptyBuyPileandEmptyDiscardPile() {
        BuyPile buypile = new BuyPile();
        DiscardPile discardPile = new DiscardPile();
        assertNull(buypile.drawCard(discardPile));
    }

}

package deck;

import org.junit.jupiter.api.Test;

import cards.DamageCard;

import static org.junit.jupiter.api.Assertions.*;

public class HandTest {
    
    @Test
    public void EmptyHandHasMaxEnergyCost() {
        Hand hand = new Hand();
        assertEquals(Integer.MAX_VALUE, hand.getMinimumEnergyCost());
    }

    @Test 
    public void HandWithCardsReturnsMinimumEnergyCost() {
        Hand hand = new Hand();
        DamageCard card1 = new DamageCard("menor", 1, 10, null, false);
        DamageCard card2 = new DamageCard("maior", 10, 5, null, false);
        hand.push(card1);
        hand.push(card2);
        assertEquals(1, hand.getMinimumEnergyCost());
    }
}

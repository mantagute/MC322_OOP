package deck;

import java.util.Random;

public class DiscardPile extends Pile {

    public DiscardPile() {
        super(MAX_DECK_SIZE);
    }

    protected void shuffle() {
        Random rand = new Random();
        for (int i = getSize() - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1); 
            swapCards(i,j);
        }
    }
}

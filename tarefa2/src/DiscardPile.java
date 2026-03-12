import java.util.Random;

public class DiscardPile extends Pile {

    public DiscardPile() {
        super(new Card[] {});
    }

    public void shuffle() {
        Random rand = new Random();
        Card[] cardsInDiscard = getCards();
        for (int i = cardsInDiscard.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1); 
            
            Card temp = cardsInDiscard[i];
            cardsInDiscard[i] = cardsInDiscard[j];
            cardsInDiscard[j] = temp;
        }
    }

}

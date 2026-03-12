import java.util.Random;

public class Enemy extends Entity {

    public Enemy(String name, int health, int energy) {
        super(name, health, energy);
    }

    public Card useMove() {
        Hand hand = super.getHand();
        Random rand = new Random();

        int random = rand.nextInt(); 

        if (super.hasEnoughEnergyForAnyCard()) {
            while (hand.getCard(random).getEnergyCost() > super.getEnergy()) {
                random = rand.nextInt(); 
            }
            return hand.extractCard(random);
        }
        return null;
    }
}

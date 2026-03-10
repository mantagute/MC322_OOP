public class Enemy extends Entity {

    private Pile hand;

    public Enemy(String name, int health, int energy) {
        super(name, health, energy);
    }

    public Card useMove() {
        int random = (int) (Math.random() * hand.getSize());
        if (super.getEnergy() >= hand.getMinimumEnergyCost()) {
            while (hand.getCard(random).getEnergyCost() > super.getEnergy()) {
                random = (int) (Math.random() * hand.getSize());
            }
            return hand.getCard(random);
        }
        return null;
    }
}

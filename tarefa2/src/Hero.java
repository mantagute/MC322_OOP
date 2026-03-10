public class Hero extends Entity {

    private Pile hand;

    public Hero(String name, int health, int energy) {
        super(name, health, energy);
        this.hand = new Pile(new Card[] {});
    }


    public boolean hasEnoughEnergyForAnyCard() {
        return super.getEnergy() >= hand.getMinimumEnergyCost();
    }
}

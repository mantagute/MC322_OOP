public class ShieldCard extends Card {
    
    private int shield;

    public ShieldCard(String name, int energyCost, int shield) {
        super(name, energyCost);
        this.shield = shield;
    }

    public void use(Entity character) {
        character.receiveShield(this);
    }

    public int getShield() {
        return shield;
    }

}
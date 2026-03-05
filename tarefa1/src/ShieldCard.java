public class ShieldCard {
    
    private String name;
    private int energyCost;
    private int shield;

    public ShieldCard(String name, int energyCost, int shield) {
        this.name = name;
        this.energyCost = energyCost;
        this.shield = shield;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public int getShield() {
        return shield;
    }

    public String getName() {
        return name;
    }
    
}
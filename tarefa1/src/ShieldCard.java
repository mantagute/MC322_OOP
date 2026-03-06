public class ShieldCard {
    
    private String name;
    private int energyCost;
    private int shield;

    public ShieldCard(String name, int energyCost, int shield) {
        this.name = name;
        this.energyCost = energyCost;
        this.shield = shield;
    }

    public void use(Hero hero) {
        hero.addShield(this);
    }

    public void use(Enemy enemy) {
        enemy.addShield(this);
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
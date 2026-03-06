public class DamageCard {
    
    private String name;
    private int cost;
    private int damage;

    public DamageCard(String name, int cost, int damage) {
        this.name = name;
        this.cost = cost;
        this.damage = damage;
    }

    public void use(Enemy enemy) {
        enemy.receiveDamage(this.damage);
    }

    public void use(Hero hero) {
        hero.receiveDamage(this.damage);
    }

    public int getEnergyCost() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }
}

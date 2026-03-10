public class DamageCard extends Card {
    
    private int damage;

    public DamageCard(String name, int cost, int damage) {
        super(name, cost);
        this.damage = damage;
    }

    public void use(Entity character) {
        character.receiveDamage(this.damage);
    }

    public int getDamage() {
        return damage;
    }
}

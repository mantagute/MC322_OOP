public class Hero {
    
    private String name;
    private int health;
    private int currentShield;
    private int energy;
    private ShieldCard shieldCard;
    private DamageCard damageCard;

    public Hero(String name, int health, int shield, int shieldGive, int ShieldCost, int damage, int DamangeCost, String damageName, String shieldName) {
        this.name = name;
        this.health = health;
        this.currentShield = shield;
        this.shieldCard = new ShieldCard(shieldName, ShieldCost, shieldGive);
        this.damageCard = new DamageCard(damageName, DamangeCost, damage);
    }

    public void receiveDamage(Enemy enemy) {
        health = health - enemy.getDamage();
    }

    public void increaseShield() {
        if (energy >= shieldCard.getEnergyCost()) {
            energy = energy - shieldCard.getEnergyCost();
            currentShield = currentShield + shieldCard.getShield();
        }
    }

    public void attack(Enemy enemy) {
        if (energy >= damageCard.getEnergyCost()) {
            energy = energy - damageCard.getEnergyCost();
            enemy.receiveDamage(damageCard.getDamage());
        }

    }
    public boolean isAlive() {
        return health > 0;
    }

}

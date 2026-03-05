public class Hero {
    
    private String name;
    private int health;
    private int currentShield;
    private int energy;

    public Hero(String name, int health, int shield, int energy) {
        this.name = name;
        this.health = health;
        this.currentShield = shield;
        this.energy = energy;
    }

    public void receiveDamage(int damage) {
        if (currentShield > damage) {
            currentShield = currentShield - damage;
        } else {
            health = health - (damage - currentShield);
            currentShield = 0;
        }
    }

    public void increaseShield(ShieldCard shieldCard) {
        if (energy >= shieldCard.getEnergyCost()) {
            energy = energy - shieldCard.getEnergyCost();
            currentShield = currentShield + shieldCard.getShield();
        }
    }

    public void attack(Enemy enemy, DamageCard damageCard) {
        if (energy >= damageCard.getEnergyCost()) {
            energy = energy - damageCard.getEnergyCost();
            enemy.receiveDamage(damageCard.getDamage());
        }
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return health > 0;
    }

}

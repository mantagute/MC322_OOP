public class Hero {
    
    private String name;
    private int health;
    private int currentShield;
    private int maxEnergy;
    private int currentEnergy;

    public Hero(String name, int health, int energy) {
        this.name = name;
        this.health = health;
        this.currentShield = 0;
        this.maxEnergy = energy;
        this.currentEnergy = energy;
    }

    public void newTurn() {
        currentEnergy = maxEnergy;
        currentShield = 0;
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
        if (currentEnergy >= shieldCard.getEnergyCost()) {
            currentEnergy = currentEnergy - shieldCard.getEnergyCost();
            shieldCard.use(this);

            System.out.println("\n" + name + " usou " + shieldCard.getName() + " e aumentou seu escudo em " + shieldCard.getShield() + "!\n");
        }
    }

    public void addShield(ShieldCard shieldCard) {
        currentShield = currentShield + shieldCard.getShield();
    }

    public void attack(Enemy enemy, DamageCard damageCard) {
        if (currentEnergy >= damageCard.getEnergyCost()) {
            currentEnergy = currentEnergy - damageCard.getEnergyCost();
            damageCard.use(enemy);

            System.out.println("\n" + name + " usou " + damageCard.getName() + " e causou " + damageCard.getDamage() + " de dano em " + enemy.getName() + "!");
        }
    }

    public boolean hasEnoughEnergyForAnyCard(DamageCard damageCard1, DamageCard damageCard2, ShieldCard shieldCard1, ShieldCard shieldCard2) {
        return currentEnergy >= damageCard1.getEnergyCost() || currentEnergy >= damageCard2.getEnergyCost() || currentEnergy >= shieldCard1.getEnergyCost() || currentEnergy >= shieldCard2.getEnergyCost();
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return currentEnergy;
    }

    public int getHealth() {
        return health;
    }

    public int getShield() {
        return currentShield;
    }

    public boolean isAlive() {
        return health > 0;
    }

}

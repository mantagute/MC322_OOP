public class Enemy {

    private String name;
    private int health;
    private int currentShield;
    private int damage;
    private int maxEnergy;
    private int currentEnergy;

    public Enemy(String name, int health, int energy) {
        this.name = name;
        this.health = health;
        this.currentShield = 0;
        this.maxEnergy = energy;
        this.currentEnergy = energy;
    }

    public String useMove(DamageCard damageCard1, DamageCard damageCard2, ShieldCard shieldCard1, ShieldCard shieldCard2) {
        double random = Math.random();
        while (currentEnergy >= damageCard1.getEnergyCost()) {
            if (random < 0.25 && currentEnergy >= damageCard1.getEnergyCost()) {
                return "attack_1";
            } else if (random < 0.5 && currentEnergy >= damageCard2.getEnergyCost()) {
                return "attack_2";
            } else if (random < 0.75 && currentEnergy >= shieldCard1.getEnergyCost()) {
                return "shield_1";
            } else if (currentEnergy >= shieldCard2.getEnergyCost()) {
                return "shield_2";
            } else {
                random = Math.random();
            }
        }
        return "no_energy";
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
            currentEnergy = currentEnergy- shieldCard.getEnergyCost();
            shieldCard.use(this);
            System.out.println(name + " usou " + shieldCard.getName() + " e aumentou seu escudo em " + shieldCard.getShield() + "!\n");
        }
    }

    public void addShield(ShieldCard shieldCard) {
        currentShield = currentShield + shieldCard.getShield();
    }

    public void attack(Hero hero, DamageCard damageCard) {
        if (currentEnergy >= damageCard.getEnergyCost()) {
            currentEnergy = currentEnergy - damageCard.getEnergyCost();
            damageCard.use(hero);
            System.out.println(name + " usou " + damageCard.getName() + " e causou " + damageCard.getDamage() + " de dano!\n");
        }
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
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

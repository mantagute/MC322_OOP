public class Enemy {

    private String name;
    private int health;
    private int currentShield;
    private int damage;
    private int energy;

    public Enemy(String name, int health, int shield, int energy) {
        this.name = name;
        this.health = health;
        this.currentShield = shield;
        this.energy = energy;
    }

    public String useMove() {
        double random = Math.random();
        while (energy >= 3) {
            if (random < 0.25 && energy >= 3) {
                return "attack_1";
            } else if (random < 0.5 && energy >= 5) {
                return "attack_2";
            } else if (random < 0.75 && energy >= 3) {
                return "shield_1";
            } else if (energy >= 5){
                return "shield_2";
            } else {
                random = Math.random();
            }
        }
        return "no_energy";
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
            System.out.println(name + " usou " + shieldCard.getName() + " e aumentou seu escudo em " + shieldCard.getShield() + "!");
        }
    }

    public void attack(Hero hero, DamageCard damageCard) {
        if (energy >= damageCard.getEnergyCost()) {
            energy = energy - damageCard.getEnergyCost();
            hero.receiveDamage(damageCard.getDamage());
            System.out.println(name + " usou " + damageCard.getName() + " e causou " + damageCard.getDamage() + " de dano!");
        }
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return health > 0;
    }
}

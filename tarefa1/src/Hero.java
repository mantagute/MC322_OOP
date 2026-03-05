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

    public String getPossibleActions(int choice, DamageCard heroDamageCard1, DamageCard heroDamageCard2, ShieldCard heroShieldCard1, ShieldCard heroShieldCard2, Enemy enemy) {
        switch (choice) {
            case 1:
                attack(enemy, heroDamageCard1);
                return heroDamageCard1.getName();
            case 2:
                attack(enemy, heroDamageCard2);
                return heroDamageCard2.getName();
            case 3:
                increaseShield(heroShieldCard1);
                return heroShieldCard1.getName();
            case 4:
                increaseShield(heroShieldCard2);
                return heroShieldCard2.getName();
            case 5:
                System.out.println("Passou a vez");
            default:
                System.out.println("Escolha inválida");
                return "";
        }
    }

}

abstract class Entity {
    private String name;
    private int health;
    private int currentShield;
    private int currentEnergy;
    private int maxEnergy;
    private static int MAX_HAND_SIZE = 5;
    private Hand hand;

    public Entity(String name, int health, int energy) {
        this.name = name;
        this.health = health;
        this.currentEnergy = energy;
        this.maxEnergy = energy;
        this.currentShield = 0;
        this.hand = new Hand();
    }

    public void receiveDamage(int damage) {
        if (currentShield > damage) {
            currentShield = currentShield - damage;
        } else {
            health = health - (damage - currentShield);
            currentShield = 0;
        }
    }

    public void attack(Entity character, DamageCard damageCard) {
        if (currentEnergy >= damageCard.getEnergyCost()) {
            currentEnergy = currentEnergy - damageCard.getEnergyCost();
            damageCard.use(character);
            System.out.println(
                    name + " usou " + damageCard.getName() + " e causou " + damageCard.getDamage() + " de dano!\n");
        }
    }

    public void increaseShield(ShieldCard shieldCard) {
        if (currentEnergy >= shieldCard.getEnergyCost()) {
            currentEnergy = currentEnergy - shieldCard.getEnergyCost();
            shieldCard.use(this);
            System.out.println(name + " usou " + shieldCard.getName() + " e aumentou seu escudo em "
                    + shieldCard.getShield() + "!\n");
        }
    }

    public void receiveShield(ShieldCard shieldCard) {
        currentShield = currentShield + shieldCard.getShield();
    }

    public boolean hasEnoughEnergyForAnyCard() {
        return getEnergy() >= hand.getMinimumEnergyCost();
    }

    public void newTurn(BuyPile buyPile) {
        currentEnergy = maxEnergy;
        currentShield = 0;

        while (hand.getCards().length < MAX_HAND_SIZE) {
            Card drawnCard = buyPile.drawCard();
            if (drawnCard != null) {
                hand.push(drawnCard);
            } else {
                break;
            }
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Hand getHand() {
        return hand;
    }

    public int getHealth() {
        return health;
    }

    public int getShield() {
        return currentShield;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return currentEnergy;
    }
}

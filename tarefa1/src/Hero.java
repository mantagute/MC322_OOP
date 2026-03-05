public class Hero {
    
    private String name;
    private int health;
    private int currentShield;

    public Hero(String name, int health, int shield) {
        this.name = name;
        this.health = health;
        this.currentShield = shield;
    }

    public void receiveDamage(int damage) {
        health = health - damage;
    }

    public void increaseShield(int shield) {
        currentShield = currentShield + shield;
    }

    public boolean isAlive() {
        return health > 0;
    }

}

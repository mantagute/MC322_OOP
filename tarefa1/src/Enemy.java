public class Enemy {
    
    private String name;
    private int health;
    private int currentShield;
    private int damage;
    
    public Enemy(String name, int health, int shield, int damage) {
        this.name = name;
        this.health = health;
        this.currentShield = shield;
        this.damage = damage;
    }
    
    public void receiveDamage(int damage) {
        health = health - damage;
    }
    
    public void attack(Hero hero) {
        hero.receiveDamage(this);
    }

    public int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return health > 0;
    }
}

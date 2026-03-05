public class Enemy {
    
    private String name;
    private int health;
    private int currentShield;
    
    public Enemy(String name, int health, int shield) {
        this.name = name;
        this.health = health;
        this.currentShield = shield;
    }
    
    public void receiveDamage(int damage) {
        health = health - damage;
    }
    
    public void attack() {}
    
    public boolean isAlive() {
        return health > 0;
    }
}

public class App {

    private int choice;
    private Hero hero;
    private Enemy enemy;
    private DamageCard heroDamageCard1;
    private DamageCard heroDamageCard2;
    private ShieldCard heroShieldCard1;
    private ShieldCard heroShieldCard2;
    private DamageCard enemyDamageCard1;
    private DamageCard enemyDamageCard2;
    private ShieldCard enemyShieldCard1;
    private ShieldCard enemyShieldCard2;

    public void start() {
        hero = new Hero("Didi Marco", 100, 100, 10);
        enemy = new Enemy("Sr. Dr. Cabo Arruda", 100, 100, 10);
        heroDamageCard1 = new DamageCard("Festa", 3, 15);
        heroDamageCard2 = new DamageCard("Rolê de Abarth", 5, 25);
        heroShieldCard1 = new ShieldCard("Drip", 3, 15);
        heroShieldCard2 = new ShieldCard("TJPP", 5, 25);
        enemyDamageCard1 = new DamageCard("Cachoeira", 3, 15);
        enemyDamageCard2 = new DamageCard("Porrada", 5, 25);
        enemyShieldCard1 = new ShieldCard("Açaí com Leite", 3, 15);
        enemyShieldCard2 = new ShieldCard("Berimbau", 5, 25);
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }
}

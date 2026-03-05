import java.util.Scanner;

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
        heroShieldCard2 = new ShieldCard("TJP", 5, 25);
        enemyDamageCard1 = new DamageCard("Cachoeira", 3, 15);
        enemyDamageCard2 = new DamageCard("Porrada", 5, 25);
        enemyShieldCard1 = new ShieldCard("Açaí com Leite", 3, 15);
        enemyShieldCard2 = new ShieldCard("Berimbau", 5, 25);
    }

    public void HeroLoop() {
        while(hero.isAlive()) {
            while(choice != 5 ) {
                System.out.println("Choose an action:");
                System.out.println("1 - Atacck with " + heroDamageCard1.getName() + " (Cost: " + heroDamageCard1.getEnergyCost() + ", Damage: " + heroDamageCard1.getDamage() + ")");
                System.out.println("2 - Atacar com " + heroDamageCard2.getName() + " (Custo: " + heroDamageCard2.getEnergyCost() + ", Dano: " + heroDamageCard2.getDamage() + ")");
                System.out.println("3 - Defender com " + heroShieldCard1.getName() + " (Custo: " + heroShieldCard1.getEnergyCost() + ", Escudo: " + heroShieldCard1.getShield() + ")");
                System.out.println("4 - Defender com " + heroShieldCard2.getName() + " (Custo: " + heroShieldCard2.getEnergyCost() + ", Escudo: " + heroShieldCard2.getShield() + ")");
                System.out.println("5 - Passar a vez");
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextInt();
                if (hero.getPossibleActions(choice, heroDamageCard1, heroDamageCard2, heroShieldCard1, heroShieldCard2, enemy) != "") {
                    break;
                } ;
                scanner.close();
            }
        }

        
    }
    public void enemyLoop() {
        String move = enemy.useMove();

        while (!move.equals("no_energy")) {
            if (move.equals("attack_1")) {
                enemy.attack(hero, enemyDamageCard1);
            } else if (move.equals("attack_2")) {
                enemy.attack(hero, enemyDamageCard2);
            } else if (move.equals("shield_1")) {
                enemy.increaseShield(enemyShieldCard1);
            } else if (move.equals("shield_2")) {
                enemy.increaseShield(enemyShieldCard2);
            }
        };

        System.out.println(enemy.getName() + " terminou seu turno!");
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();

        while (app.hero.isAlive() && app.enemy.isAlive()) {
            app.heroLoop();
            app.enemyLoop();
        }

        if (app.hero.isAlive()) {
            System.out.println(app.hero.getName() + " venceu!");
            System.out.println(app.hero.getName() + " ainda não acabou o experimento. F carona...");

        } else {
            System.out.println(app.enemy.getName() + " venceu!");
            System.out.println("Não sobrou nada...");
        }
    }
}

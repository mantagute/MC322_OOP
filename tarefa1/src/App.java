import java.util.Scanner;

public class App {

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

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void Wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void start() {
        hero = new Hero("Didi Marco", 100, 10);
        enemy = new Enemy("Sr. Dr. Cabo Arruda", 100, 10);
        heroDamageCard1 = new DamageCard("Festa", 3, 15);
        heroDamageCard2 = new DamageCard("Rolê de Abarth", 5, 25);
        heroShieldCard1 = new ShieldCard("Drip", 3, 15);
        heroShieldCard2 = new ShieldCard("TJP", 5, 25);
        enemyDamageCard1 = new DamageCard("Cachoeira", 3, 15);
        enemyDamageCard2 = new DamageCard("Porrada", 5, 25);
        enemyShieldCard1 = new ShieldCard("Açaí com Leite", 3, 15);
        enemyShieldCard2 = new ShieldCard("Berimbau", 5, 25);

    }

    public void heroTurn(Scanner scanner) {
        hero.newTurn();
        boolean isTurnOver = false;

        while (!isTurnOver && hero.isAlive() && enemy.isAlive()) {
            if (!hero.hasEnoughEnergyForAnyCard(heroDamageCard1, heroDamageCard2, heroShieldCard1, heroShieldCard2)) {
                System.out.println(
                        "\n" + hero.getName() + " sem energia, passando a vez para " + enemy.getName() + "...\n");
                App.Wait(2000);
                isTurnOver = true;
                continue;
            }

            System.out.println("\n=== TURNO DE " + hero.getName().toUpperCase() + " ===\n");
            System.out.println(hero.getName() + " | Vida: " + hero.getHealth() +
                    " | Escudo: " + hero.getShield() +
                    " | Energia: " + hero.getEnergy() + "\nvs");
            System.out.println(enemy.getName() + " | Vida: " + enemy.getHealth() +
                    " | Escudo: " + enemy.getShield() + "\n");
            System.out.println("1 - Atacar com " + heroDamageCard1.getName() + " (Custo: "
                    + heroDamageCard1.getEnergyCost() + ", Dano: " + heroDamageCard1.getDamage() + ")");
            System.out.println("2 - Atacar com " + heroDamageCard2.getName() + " (Custo: "
                    + heroDamageCard2.getEnergyCost() + ", Dano: " + heroDamageCard2.getDamage() + ")");
            System.out.println("3 - Defender com " + heroShieldCard1.getName() + " (Custo: "
                    + heroShieldCard1.getEnergyCost() + ", Escudo: " + heroShieldCard1.getShield() + ")");
            System.out.println("4 - Defender com " + heroShieldCard2.getName() + " (Custo: "
                    + heroShieldCard2.getEnergyCost() + ", Escudo: " + heroShieldCard2.getShield() + ")");
            System.out.println("5 - Passar a vez\n");
            System.out.println("Escolha uma ação:");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    hero.attack(enemy, heroDamageCard1);
                    break;
                case 2:
                    hero.attack(enemy, heroDamageCard2);
                    break;
                case 3:
                    hero.increaseShield(heroShieldCard1);
                    break;
                case 4:
                    hero.increaseShield(heroShieldCard2);
                    break;
                case 5:
                    isTurnOver = true;
                    break;
                default:
                    System.out.println("Escolha inválida, tente novamente.\n");
                    break;
            }

            App.Wait(2000);

            if (!enemy.isAlive()) {
                isTurnOver = true;
            }
        }
    }

    public void enemyTurn() {
        if (!enemy.isAlive()) {
            return;
        }

        enemy.newTurn();

        System.out.println("\n=== TURNO DE " + enemy.getName().toUpperCase() + " ===\n");
        System.out.println(hero.getName() + " | Vida: " + hero.getHealth() +
                " | Escudo: " + hero.getShield() +
                " | Energia: " + hero.getEnergy() + "\nvs");
        System.out.println(enemy.getName() + " | Vida: " + enemy.getHealth() +
                " | Escudo: " + enemy.getShield() + "\n");
        String move = enemy.useMove(enemyDamageCard1, enemyDamageCard2, enemyShieldCard1, enemyShieldCard2);

        while (!move.equals("no_energy") && hero.isAlive() && enemy.isAlive()) {
            if (move.equals("attack_1")) {
                enemy.attack(hero, enemyDamageCard1);
            } else if (move.equals("attack_2")) {
                enemy.attack(hero, enemyDamageCard2);
            } else if (move.equals("shield_1")) {
                enemy.increaseShield(enemyShieldCard1);
            } else if (move.equals("shield_2")) {
                enemy.increaseShield(enemyShieldCard2);
            }

            if (!hero.isAlive()) {
                break;
            }

            move = enemy.useMove(enemyDamageCard1, enemyDamageCard2, enemyShieldCard1, enemyShieldCard2);
            App.Wait(2000);
        }
        ;
        System.out.println(enemy.getName() + " terminou seu turno!\n");
        App.Wait(2000);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        App app = new App();
        App.clearScreen();
        app.start();

        while (app.hero.isAlive() && app.enemy.isAlive()) {
            app.heroTurn(scanner);
            app.enemyTurn();
            App.clearScreen();
        }

        if (app.hero.isAlive()) {
            System.out.println(app.hero.getName() + " venceu!\n");
            System.out.println(app.enemy.getName() + "," + app.hero.getName() + " ainda não terminou o experimento. F carona...");
            App.Wait(10000);
        } else {
            System.out.println(app.enemy.getName() + " venceu!\n");
            System.out.println("Não sobrou nada...");
            App.Wait(10000);
        }
        scanner.close();
    }
}

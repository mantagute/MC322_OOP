import java.util.Scanner;

import cards.Card;
import deck.BuyPile;
import deck.DiscardPile;
import entities.Enemy;
import entities.Hero;

public class App {

    private Hero hero;
    private Enemy enemy;

    public static void gameIntro() {
        System.out.println("  _____  _____ _____ _____   __  __          _____   _____ ____  ");
        System.out.println(" |  __ \\|_   _|  __ \\_   _| |  \\/  |   /\\   |  __ \\ / ____/ __ \\ ");
        System.out.println(" | |  | | | | | |  | || |   | \\  / |  /  \\  | |__) | |   | |  | |");
        System.out.println(" | |  | | | | | |  | || |   | |\\/| | / /\\ \\ |  _  /| |   | |  | |");
        System.out.println(" | |__| |_| |_| |__| || |_  | |  | |/ ____ \\| | \\ \\| |___| |__| |");
        System.out.println(" |_____/|_____|_____/_____| |_|  |_/_/    \\_\\_|  \\_\\\\_____\\____/ ");

        System.out.println("\n                            V S                                  \n");

        System.out.println("  _____ _____     _____  _____      _____          ____   ____  ");
        System.out.println(" / ____|  __ \\   |  __ \\|  __ \\    / ____|   /\\   |  _ \\ / __ \\ ");
        System.out.println("| (___ | |__) |  | |  | | |__) |  | |       /  \\  | |_) | |  | |");
        System.out.println(" \\___ \\|  _  /   | |  | |  _  /   | |      / /\\ \\ |  _ <| |  | |");
        System.out.println(" ____) | | \\ \\   | |__| | | \\ \\   | |____ / ____ \\| |_) | |__| |");
        System.out.println("|_____/|_|  \\_\\  |_____/|_|  \\_\\   \\_____/_/    \\_\\____/ \\____/ ");

        System.out.println("          /\\    |  __ \\|  __ \\| |  | |  __ \\   /\\     ");
        System.out.println("         /  \\   | |__) | |__) | |  | | |  | | /  \\    ");
        System.out.println("        / /\\ \\  |  _  /|  _  /| |  | | |  | |/ /\\ \\   ");
        System.out.println("       / ____ \\ | | \\ \\| | \\ \\| |__| | |__| / ____ \\  ");
        System.out.println("      /_/    \\_\\_|  \\_\\_|  \\_\\\\____/|_____/_/    \\_\\ ");
    }

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

        BuyPile heroBuyPile = new BuyPile(new Card[0]);
        DiscardPile heroDiscardPile = new DiscardPile();

        BuyPile enemyBuyPile = new BuyPile(new Card[0]);
        DiscardPile enemyDiscardPile = new DiscardPile();
    }

    public void heroTurn(Scanner scanner, BuyPile heroBuyPile, BuyPile enemyBuyPile, DiscardPile heroDiscardPile, DiscardPile enemyDiscardPile) {
        hero.newTurn(heroBuyPile);
        boolean isTurnOver = false;

        while (!isTurnOver && hero.isAlive() && enemy.isAlive()) {
            if (!hero.hasEnoughEnergyForAnyCard()) {
                System.out.println(
                        "\n" + hero.getName() + " sem energia, passando a vez para " + enemy.getName() + "...\n");
                App.Wait(2000);
                isTurnOver = true;
                break;
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
        Card card = enemy.useMove();
        // refatorar para usar useMove retornando Card.
        while (card != null) {

            card = enemy.useMove();
            App.Wait(2000);
        }
        ;
        System.out.println(enemy.getName() + " terminou seu turno!\n");
        App.Wait(2000);
    }

    public static void main(String[] args) throws Exception {
        App.clearScreen();
        App.gameIntro();
        App.Wait(5000);
        Scanner scanner = new Scanner(System.in);
        App app = new App();
        app.start();

        while (app.hero.isAlive() && app.enemy.isAlive()) {
            app.heroTurn(scanner);
            app.enemyTurn();
            App.clearScreen();
        }

        if (app.hero.isAlive()) {
            System.out.println(app.hero.getName() + " venceu!\n");
            System.out.println(app.enemy.getName() + ", " + app.hero.getName() + " ainda não terminou o experimento. F carona...");
            App.Wait(10000);
        } else {
            System.out.println(app.enemy.getName() + " venceu!\n");
            System.out.println("Não sobrou nada...");
            App.Wait(10000);
        }
        scanner.close();
    }
}

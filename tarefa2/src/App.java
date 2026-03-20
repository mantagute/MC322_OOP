import java.util.Scanner;

import cards.Card;
import cards.DamageCard;
import deck.BuyPile;
import deck.DiscardPile;
import entities.Enemy;
import entities.Hero;

public class App {

    private Hero hero;
    private Enemy enemy;
    private BuyPile heroBuyPile;
    private DiscardPile heroDiscardPile;
    private BuyPile enemyBuyPile;
    private DiscardPile enemyDiscardPile;

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
        hero = Data.heroes.get(0);
        enemy = Data.azoides.get(0);

        heroBuyPile = new BuyPile();
        Data.fillPile(heroBuyPile, Data.heroDamageCards);
        Data.fillPile(heroBuyPile, Data.heroShieldCards);
        heroDiscardPile = new DiscardPile();

        enemyBuyPile = new BuyPile();
        Data.fillPile(enemyBuyPile, Data.azoideDamageCards);
        Data.fillPile(enemyBuyPile, Data.azoideShieldCards);
        enemyDiscardPile = new DiscardPile();

        heroBuyPile.shuffle();
        enemyBuyPile.shuffle();
    }

    public void heroTurn(Scanner scanner) {
        hero.resetShield();
        hero.newTurn(heroBuyPile, heroDiscardPile);
        boolean isTurnOver = false;

        while (!isTurnOver && hero.isAlive() && enemy.isAlive()) {
            App.clearScreen();
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

            for (int i = 0; i < hero.getHandSize(); i++) {
                Card card = hero.getCardFromHand(i);
                boolean isDamageCard = card instanceof DamageCard;
                System.out.println((i + 1) + " - " + card.getName() + (isDamageCard ? " (Dano: " + card.getEffectValue() + ")" : " (Escudo: " + card.getEffectValue() + ")") + " (Custo: " + card.getEnergyCost() + ")");
            }

            System.out.println((hero.getHandSize() + 1) + " - Passar a vez\n");
            System.out.println("Escolha uma ação:");
            int choice = scanner.nextInt();

            if (choice == hero.getHandSize() + 1) {
                System.out.println("\n" + hero.getName() + " passa a vez para " + enemy.getName() + "...\n");
                App.Wait(2000);
                isTurnOver = true;
                continue;
            } else if (choice < 1 || choice > hero.getHandSize()) {
                System.out.println("\nOpção inválida. Tente novamente.");
                App.Wait(2000);
                continue;
            } else {
                Card chosenCard = hero.getCardFromHand(choice - 1);
                if (chosenCard.getEnergyCost() > hero.getEnergy()) {
                    System.out.println("\nEnergia insuficiente para usar esta carta. Tente novamente.");
                    App.Wait(1500);
                    continue;
                }
                hero.useCard(choice - 1, enemy, heroDiscardPile);
                App.Wait(2000);
            }

            if (!enemy.isAlive()) {
                isTurnOver = true;
            }
        }
    }

    public void enemyTurn() {
        App.clearScreen();
        enemy.newTurn(enemyBuyPile, enemyDiscardPile);
        System.out.println(enemy.prepareForBattle());
        App.Wait(2000);
    }

    public static void main(String[] args) throws Exception {
        App.gameIntro();
        App.Wait(3000);
        Scanner scanner = new Scanner(System.in);
        App app = new App();
        app.start();

        while (app.hero.isAlive() && app.enemy.isAlive()) {
            app.enemyTurn();
            app.heroTurn(scanner);
            app.enemy.resetShield();
            if (app.enemy.isAlive()) {
                App.clearScreen();
                System.out.println("\n=== ATAQUE DE " + app.enemy.getName().toUpperCase() + " ===\n");
                app.enemy.executeEnemyStrategy(app.hero, app.enemyDiscardPile);
                App.Wait(2000);
            }
        }
        App.clearScreen();
        if (app.hero.isAlive()) {
            System.out.println(app.hero.getName() + " venceu!\n");
            System.out.println(app.enemy.getName() + ", " + app.hero.getName() + " ainda não terminou o experimento. F carona...");
        } else {
            System.out.println(app.enemy.getName() + " venceu!\n");
            System.out.println("Não sobrou nada...");
        }
        App.Wait(10000);
        scanner.close();
    }
}

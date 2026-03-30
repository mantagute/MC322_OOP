import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import cards.Card;
import deck.BuyPile;
import deck.DiscardPile;
import entities.Enemy;
import entities.Hero;
import gameOrchestrator.Data;
import observer.Publisher;

public class App {

    private Hero hero;
    private List<Enemy> enemies = new ArrayList<>();
    private BuyPile heroBuyPile;
    private DiscardPile heroDiscardPile;
    private Publisher publisher = new Publisher();

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

        heroBuyPile = new BuyPile();
        Data.fillPile(heroBuyPile, Data.heroDamageCards);
        Data.fillPile(heroBuyPile, Data.heroShieldCards);
        Data.fillPile(heroBuyPile, Data.heroEffectCards(publisher));
        heroBuyPile.shuffle();
        heroDiscardPile = new DiscardPile();

        enemies.add(Data.createAzoide("Sr. Doutor Cabo Arruda", 100, 10, publisher));
        enemies.add(Data.createBzoide("3L", 100, 10, publisher));
    }

    public void heroTurn(Scanner scanner) {
        hero.resetShield();
        hero.newTurn(heroBuyPile, heroDiscardPile);
        boolean isTurnOver = false;

        while (!isTurnOver && hero.isAlive() && enemies.stream().anyMatch(enemy -> enemy.isAlive())) {
            App.clearScreen();
            if (!hero.hasEnoughEnergyForAnyCard()) {
                System.out.println(
                        "\n" + hero.getName() + " sem energia, passando a vez para a gang dos Paraenses ...\n");
                App.Wait(2000);
                isTurnOver = true;
                break;
            }

            System.out.println("\n=== TURNO DE " + hero.getName().toUpperCase() + " ===\n");
            System.out.println(hero.getName() + " | Vida: " + String.format("%.1f", hero.getHealth()) +
                    " | Escudo: " + String.format("%.1f", hero.getShield()) +
                    " | Energia: " + hero.getEnergy() +
                    " | Efeitos: " + hero.getEffectString() + " \n");
            System.out.println("vs\n");

            for (Enemy enemy : enemies) {
                if (enemy.isAlive()) {
                    System.out.println(enemy.getName() + " | Vida: " + String.format("%.1f", enemy.getHealth()) +
                            " | Escudo: " + String.format("%.1f", enemy.getShield()) +
                            " | Efeitos: " + enemy.getEffectString() + "\n");
                }
            }

            for (int i = 0; i < hero.getHandSize(); i++) {
                Card card = hero.getCardFromHand(i);
                System.out.println((i + 1) + " - " + card.getName() + card.getDetails() + " (Custo: "
                        + card.getEnergyCost() + ")" + (card.isMultiTarget() ? " -> Ataca todos os inimigos!" : ""));
            }

            System.out.println((hero.getHandSize() + 1) + " - Passar a vez\n");
            System.out.println("Escolha uma ação:");
            int choice = scanner.nextInt();

            if (choice == hero.getHandSize() + 1) {
                System.out.println("\n" + hero.getName() + " passa a vez para a gang dos Paraenses ...\n");
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

                if (chosenCard.isMultiTarget()) {
                    boolean first = true;
                    for (Enemy enemy : enemies) {
                        if (enemy.isAlive()) {
                            if (first) {
                                hero.useCard(choice - 1, enemy, heroDiscardPile);
                                first = false;
                            } else {
                                chosenCard.useCard(hero, enemy);
                            }
                        }
                    }
                } else if (chosenCard.isSelfTarget()) {
                    hero.useCard(choice - 1, hero, heroDiscardPile);
                } else {
                    List<Enemy> aliveEnemies = enemies.stream()
                            .filter(Enemy::isAlive)
                            .collect(Collectors.toList());
                    if (aliveEnemies.size() == 1) {
                        hero.useCard(choice - 1, aliveEnemies.get(0), heroDiscardPile);
                    } else {
                        System.out.println("\nEscolha qual inimigo atacar.");
                        for (Enemy enemy : aliveEnemies) {
                            System.out.println((aliveEnemies.indexOf(enemy) + 1) + " - " + enemy.getName() + " | Vida: "
                                    + String.format("%.1f", enemy.getHealth()) +
                                    " | Escudo: " + String.format("%.1f", enemy.getShield()) +
                                    " | Efeitos: " + enemy.getEffectString() + "\n");
                        }
                        int chosenEnemy = scanner.nextInt();
                        if (chosenEnemy < 1 || chosenEnemy > aliveEnemies.size()) {
                            System.out.println("\nOpção inválida. Tente novamente.");
                            App.Wait(2000);
                            continue;
                        } else {
                            hero.useCard(choice - 1, aliveEnemies.get(chosenEnemy - 1), heroDiscardPile);
                        }
                    }
                }
                App.Wait(2000);
            }

            boolean allEnemiesDead = enemies.stream().allMatch(enemy -> !enemy.isAlive());
            if (allEnemiesDead) {
                isTurnOver = true;
            }
        }
    }

    public void enemyTurn() {
        App.clearScreen();
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                enemy.newTurn();
                System.out.println(enemy.prepareForBattle());
                App.Wait(4000);
            }
        }
    }

    private void notifyAndClean(String event, entities.Entity user, entities.Entity target) {
        publisher.notify(event, user, target);
        user.manageEffects();
    }

    public static void main(String[] args) throws Exception {
        App.gameIntro();
        App.Wait(3000);
        Scanner scanner = new Scanner(System.in);
        App app = new App();
        app.start();

        while (app.hero.isAlive() && app.enemies.stream().anyMatch(enemy -> enemy.isAlive())) {
            app.enemyTurn();
            app.heroTurn(scanner);
            for (Enemy enemy : app.enemies) {
                if (enemy.isAlive()) {
                    enemy.resetShield();
                    App.clearScreen();
                    System.out.println("\n=== ATAQUE DE " + enemy.getName().toUpperCase() + " ===\n");
                    if (enemy.isAlive()) {
                        enemy.executeEnemyStrategy(app.hero);
                    }
                    
                    App.Wait(2000);
                }
            }
            app.notifyAndClean("FIM_TURNO", app.hero, app.enemies.get(0));
            for (Enemy enemy : app.enemies) {
                app.notifyAndClean("FIM_TURNO", enemy, app.hero);
            }
        }
        App.clearScreen();
        if (app.hero.isAlive()) {
            System.out.println(app.hero.getName() + " venceu!\n");
            System.out.println(app.enemies.get(0).getName() + ", " + app.hero.getName()
                    + " ainda não terminou o experimento. F carona...");
        } else {
            System.out.println(app.enemies.get(0).getName() + "e " + app.enemies.get(1).getName() + " venceram!\n");
            System.out.println("Não sobrou nada...");
        }
        App.Wait(10000);
        scanner.close();
    }
}

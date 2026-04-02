package gameOrchestrator;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import cards.Card;
import deck.BuyPile;
import deck.DiscardPile;
import entities.Enemy;
import entities.Hero;
import observer.Publisher;

/**
 * Classe principal do jogo — orquestra o loop de batalha entre o herói e os inimigos.
 *
 * <p>Responsabilidades desta classe:
 * <ul>
 *   <li>Inicializar as entidades e baralhos do jogo via {@link Data};</li>
 *   <li>Gerenciar os turnos do herói e dos inimigos;</li>
 *   <li>Disparar notificações de fim de turno para o sistema Observer;</li>
 *   <li>Renderizar o estado do combate no terminal a cada ação.</li>
 * </ul>
 *
 * <p>O ponto de entrada da aplicação é o método {@link #main(String[])}.
 *
 * @see Data
 * @see observer.Publisher
 */
public class App {

    /** Herói controlado pelo jogador. */
    private Hero hero;

    /** Lista de inimigos presentes no combate. */
    private List<Enemy> enemies = new ArrayList<>();

    /** Pilha de compra do herói. */
    private BuyPile heroBuyPile;

    /** Pilha de descarte do herói. */
    private DiscardPile heroDiscardPile;

    /** Publisher central do padrão Observer, compartilhado por todos os efeitos. */
    private Publisher publisher = new Publisher();

    // =========================================================================
    // Utilitários de terminal
    // =========================================================================

    /**
     * Exibe a tela de introdução do jogo com arte ASCII representando
     * o confronto entre o herói Didi Marco e os inimigos Sr. Dr. Cabo Arruda e 3L.
     */
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

    /**
     * Limpa o terminal enviando o código de escape ANSI {@code \033[H\033[2J}.
     * Funciona em terminais compatíveis com ANSI (Linux/macOS).
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Pausa a execução pelo tempo especificado em milissegundos.
     * Utilizado para dar tempo ao jogador de ler as mensagens exibidas no terminal.
     *
     * @param ms tempo de espera em milissegundos
     */
    public static void Wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // =========================================================================
    // Inicialização
    // =========================================================================

    /**
     * Inicializa o estado do jogo: cria o herói, popula e embaralha seu baralho,
     * e instancia os inimigos conforme definido em {@link Data}.
     */
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

    // =========================================================================
    // Turnos
    // =========================================================================

    /**
     * Executa o turno completo do herói.
     *
     * <p>O herói tem seu escudo zerado, compra 5 cartas e pode jogar cartas até
     * ficar sem energia ou optar por passar a vez. Para cartas com múltiplos alvos,
     * o efeito é aplicado a todos os inimigos vivos. Para cartas de alvo único com
     * mais de um inimigo vivo, o jogador escolhe qual atacar.
     *
     * @param scanner leitor de entrada do teclado utilizado para capturar escolhas do jogador
     */
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
                System.out.println((i + 1) + " - " + card.getName() + card.getDetails() +
                        " (Custo: " + card.getEnergyCost() + ")" +
                        (card.isMultiTarget() ? " -> Ataca todos os inimigos!" : ""));
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
                            System.out.println((aliveEnemies.indexOf(enemy) + 1) + " - " + enemy.getName() +
                                    " | Vida: " + String.format("%.1f", enemy.getHealth()) +
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

    /**
     * Executa o turno dos inimigos vivos.
     *
     * <p>Para cada inimigo vivo: inicia seu novo turno (compra cartas), define e anuncia
     * sua estratégia para o turno atual. A execução das cartas contra o herói ocorre
     * separadamente, no loop principal de {@link #main(String[])}.
     */
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

    // =========================================================================
    // Observer
    // =========================================================================

    /**
     * Dispara o evento especificado para todos os subscribers via {@link Publisher}
     * e em seguida remove da entidade os efeitos com acúmulos zerados.
     *
     * @param event  nome do evento a ser disparado (ex.: {@code "FIM_TURNO"})
     * @param user   entidade que originou o evento
     * @param target entidade alvo do evento
     */
    private void notifyAndClean(String event, entities.Entity user, entities.Entity target) {
        publisher.notify(event, user, target);
        user.manageEffects();
    }

    // =========================================================================
    // Ponto de entrada
    // =========================================================================

    /**
     * Ponto de entrada da aplicação.
     *
     * <p>Fluxo principal:
     * <ol>
     *   <li>Exibe a introdução e inicializa o jogo;</li>
     *   <li>Repete o loop de batalha enquanto o herói e ao menos um inimigo estiverem vivos:
     *     <ol>
     *       <li>Turno dos inimigos: anúncio da estratégia;</li>
     *       <li>Turno do herói: jogador escolhe cartas;</li>
     *       <li>Execução dos ataques dos inimigos contra o herói;</li>
     *       <li>Notificação de {@code "FIM_TURNO"} para herói e inimigos,
     *           ativando efeitos como Poison e o decaimento de Strength.</li>
     *     </ol>
     *   </li>
     *   <li>Exibe a mensagem de vitória ou derrota.</li>
     * </ol>
     *
     * @param args argumentos de linha de comando (não utilizados)
     * @throws Exception se ocorrer um erro inesperado durante a execução
     */
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
                    enemy.executeEnemyStrategy(app.hero);
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
            System.out.println(app.enemies.get(0).getName() + ", " + app.hero.getName() + " ainda não terminou o experimento. F carona...");
        } else {
            System.out.println(app.enemies.get(0).getName() + " e " + app.enemies.get(1).getName() + " venceram!\n");
            System.out.println("Não sobrou nada...");
        }
        App.Wait(10000);
        scanner.close();
    }
}

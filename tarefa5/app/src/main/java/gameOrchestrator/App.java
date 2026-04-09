package gameOrchestrator;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import gamePath.Node;
import gamePath.TreePath;
import cards.Card;
import deck.BuyPile;
import deck.DiscardPile;
import entities.Enemy;
import entities.Entity;
import entities.Hero;
import gameOrchestrator.Data.EnemyDefinition;
import observer.Publisher;

/**
 * Classe principal do jogo — orquestra o loop de batalha entre o herói e os
 * inimigos.
 *
 * <p>
 * Responsabilidades desta classe:
 * <ul>
 * <li>Inicializar as entidades e baralhos do jogo via {@link Data};</li>
 * <li>Gerenciar os turnos do herói e dos inimigos;</li>
 * <li>Disparar notificações de fim de turno para o sistema Observer;</li>
 * <li>Delegar toda a renderização visual a {@link UserInterface}.</li>
 * </ul>
 *
 * <p>
 * Princípios de POO aplicados:
 * <ul>
 * <li><b>Encapsulamento:</b> todos os campos de instância são {@code private},
 * sendo acessados apenas pelos métodos desta classe ou por getters explícitos,
 * evitando acesso direto via {@code app.campo} de fora da classe.</li>
 * <li><b>Responsabilidade única (SRP):</b> {@code App} orquestra o fluxo do
 * jogo,
 * enquanto {@link UserInterface} cuida exclusivamente da apresentação e
 * {@link Data} da criação de entidades.</li>
 * <li><b>Separação de interesses:</b> a lógica de negócio (turno, combate,
 * Observer)
 * está separada da lógica de renderização (UserInterface) e de dados
 * (Data).</li>
 * </ul>
 *
 * <p>
 * O ponto de entrada da aplicação é o método {@link #main(String[])}.
 *
 * @see Data
 * @see UserInterface
 * @see observer.Publisher
 */
public class App {

    // =========================================================================
    // Campos de instância (privados — encapsulamento)
    // =========================================================================

    /** Herói controlado pelo jogador. */
    private Hero hero;

    /** Lista de inimigos presentes no combate. */
    private List<Enemy> enemies = new ArrayList<>();

    /** Árvore que representa o caminho do jogo. */
    private TreePath treePath;

    /** Nó atual na árvore do jogo. */
    private Node currentNode;

    /**
     * Pilha de compra do herói. Reabastecida automaticamente pelo descarte quando
     * vazia.
     */
    private BuyPile heroBuyPile;

    /** Pilha de descarte do herói. Recebe as cartas jogadas durante o turno. */
    private DiscardPile heroDiscardPile;
    
    /**
     * Publisher central do padrão Observer.
     *
     * <p>
     * Compartilhado por todos os efeitos do jogo para garantir que um único
     * barramento de eventos gerencie as assinaturas e notificações.
     */
    private Publisher publisher = new Publisher();
    // =========================================================================
    // Utilitários de terminal
    // =========================================================================

    /**
     * Exibe a tela de introdução do jogo com arte ASCII colorida via ANSI.
     *
     * <p>
     * Imprime o título "DIDI MARCO" versus "SR. DR. CABO ARRUDA" em arte
     * ASCII, usando as constantes de cor definidas em {@link UserInterface}.
     */
    public static void gameIntro() {
        System.out.println(UserInterface.BOLD + UserInterface.BCYAN);
        System.out.println("  _____  _____ _____ _____   __  __          _____   _____ ____  ");
        System.out.println(" |  __ \\|_   _|  __ \\_   _| |  \\/  |   /\\   |  __ \\ / ____/ __ \\ ");
        System.out.println(" | |  | | | | | |  | || |   | \\  / |  /  \\  | |__) | |   | |  | |");
        System.out.println(" | |  | | | | | |  | || |   | |\\/| | / /\\ \\ |  _  /| |   | |  | |");
        System.out.println(" | |__| |_| |_| |__| || |_  | |  | |/ ____ \\| | \\ \\| |___| |__| |");
        System.out.println(" |_____/|_____|_____/_____| |_|  |_/_/    \\_\\_|  \\_\\\\_____\\____/ ");
        System.out.println(UserInterface.RESET);

        System.out.println(
                UserInterface.BOLD + UserInterface.WHITE + "                            V S" + UserInterface.RESET);
        System.out.println();

        System.out.println(UserInterface.BOLD + UserInterface.BRED);
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
        System.out.println(UserInterface.RESET);
    }

    /**
     * Pausa a execução da thread atual pelo tempo especificado.
     *
     * <p>
     * Caso a thread seja interrompida durante a espera, o flag de interrupção
     * é restaurado via {@link Thread#interrupt()} para que o chamador possa
     * tratá-lo.
     *
     * @param ms tempo de espera em milissegundos; valores negativos são tratados
     *           como zero pelo {@link Thread#sleep(long)}
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
     * Inicializa o estado do jogo: instancia o herói, monta e embaralha seu
     * baralho,
     * e cria os inimigos conforme definido em {@link Data}.
     *
     * <p>
     * Este método deve ser chamado exatamente uma vez, antes de qualquer chamada
     * a {@link #heroTurn(Scanner)} ou {@link #enemyTurn()}.
     */
    public void start() {
        hero = Data.heroes.get(0);

        treePath = new TreePath(Data.enemies);
        currentNode = treePath.getRoot();

        heroBuyPile = new BuyPile();
        Data.fillPile(heroBuyPile, Data.heroDamageCards);
        Data.fillPile(heroBuyPile, Data.heroShieldCards);
        Data.fillPile(heroBuyPile, Data.heroEffectCards(publisher));
        heroBuyPile.shuffle();
        heroDiscardPile = new DiscardPile();
    }

    public void startNewFase(Node currentNode, boolean isGoingLeft) {
        enemies.clear();
        publisher.resetPublisher();
        if (isGoingLeft) {
            this.currentNode = currentNode.getLeftNode();
        } else {
            this.currentNode = currentNode.getRightNode();
        }

        if (this.currentNode != null) {
            for (EnemyDefinition enemyDef : this.currentNode.getEnemiesDefinitions()) {
                Enemy enemy;
                if (enemyDef.type() == EnemyDefinition.EnemyType.AZOIDE) {
                    enemy = new entities.enemies.Azoide(enemyDef.name(), enemyDef.health(), enemyDef.energy());
                } else {
                    enemy = new entities.enemies.Bzoide(enemyDef.name(), enemyDef.health(), enemyDef.energy());
                }
                enemy.initializePublisher(publisher);
                enemies.add(enemy);
            }
        }
    }

    // =========================================================================
    // Getters (encapsulamento — acesso controlado ao estado interno)
    // =========================================================================

    /**
     * Retorna o herói controlado pelo jogador.
     *
     * @return o {@link Hero} atual; {@code null} se {@link #start()} ainda não foi
     *         chamado
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * Retorna a lista completa de inimigos (vivos e derrotados).
     *
     * @return lista imutável de {@link Enemy}; nunca {@code null}
     */
    public List<Enemy> getEnemies() {
        return List.copyOf(enemies);
    }

    // =========================================================================
    // Renderização do estado de combate
    // =========================================================================

    /**
     * Renderiza o estado atual do combate no terminal.
     *
     * <p>
     * Exibe o status do herói (com energia) e, abaixo, os status de todos os
     * inimigos vivos em uma única linha separada por {@code |}.
     */
    private void printCombatState() {
        UserInterface.printEntityStatus(hero, true);
        System.out.println();
        System.out.println(UserInterface.DIM + "  vs" + UserInterface.RESET);
        System.out.println();

        List<Enemy> aliveEnemies = enemies.stream()
                .filter(Enemy::isAlive)
                .collect(Collectors.toList());

        if (!aliveEnemies.isEmpty()) {
            UserInterface.printEnemiesInline(aliveEnemies);
        }

        System.out.println();
        UserInterface.printDivider();
        System.out.println();
    }

    // =========================================================================
    // Turnos
    // =========================================================================

    /**
     * Executa o turno completo do herói.
     *
     * <p>
     * O turno encerra-se quando ocorre uma das seguintes condições:
     * <ul>
     * <li>O jogador escolhe passar a vez;</li>
     * <li>O herói não possui energia suficiente para nenhuma carta;</li>
     * <li>Todos os inimigos são derrotados durante o turno;</li>
     * <li>O herói é derrotado.</li>
     * </ul>
     *
     * <p>
     * Durante o turno, o herói pode jogar cartas de dano (escolhendo um alvo
     * específico ou atacando todos com cartas multi-alvo), cartas de escudo
     * (auto-alvo) ou qualquer outra carta configurada.
     *
     * @param scanner leitor de entrada do teclado; não deve ser {@code null}
     */
    public void heroTurn(Scanner scanner) {
        hero.resetShield();
        hero.newTurn(heroBuyPile, heroDiscardPile);
        boolean isTurnOver = false;

        while (!isTurnOver && hero.isAlive() && enemies.stream().anyMatch(Enemy::isAlive)) {

            UserInterface.clearScreen();

            // Sem energia — passa automaticamente
            if (!hero.hasEnoughEnergyForAnyCard()) {
                UserInterface.printHeroTurnHeader(hero.getName());
                printCombatState();
                UserInterface.printNoEnergy(hero.getName());
                Wait(2000);
                isTurnOver = true;
                break;
            }

            UserInterface.printHeroTurnHeader(hero.getName());
            printCombatState();
            UserInterface.printHand(hero, hero.getHandSize());
            UserInterface.printPassOption(hero.getHandSize() + 1);
            UserInterface.printChoicePrompt();

            int choice = scanner.nextInt();

            // Passar a vez
            if (choice == hero.getHandSize() + 1) {
                UserInterface.printHeroPass(hero.getName());
                Wait(1500);
                isTurnOver = true;
                continue;
            }

            // Opção fora do intervalo válido
            if (choice < 1 || choice > hero.getHandSize()) {
                UserInterface.printError("Opção inválida. Tente novamente.");
                Wait(1500);
                continue;
            }

            Card chosenCard = hero.getCardFromHand(choice - 1);

            // Energia insuficiente para a carta escolhida
            if (chosenCard.getEnergyCost() > hero.getEnergy()) {
                UserInterface.printWarning("Energia insuficiente para esta carta.");
                Wait(1500);
                continue;
            }

            // Jogar a carta conforme seu modo de alvo
            if (chosenCard.isMultiTarget()) {
                applyCardToAllEnemies(choice - 1, chosenCard);
            } else if (chosenCard.isSelfTarget()) {
                hero.useCard(choice - 1, hero, heroDiscardPile);
            } else {
                applyCardToSingleEnemy(choice - 1, scanner);
            }

            Wait(1800);

            if (enemies.stream().allMatch(e -> !e.isAlive())) {
                isTurnOver = true;
            }
        }
    }

    /**
     * Aplica uma carta de multi-alvo a todos os inimigos vivos.
     *
     * <p>
     * O descarte da carta é realizado apenas na primeira aplicação (via
     * {@link Hero#useCard}); nas demais, a carta é aplicada diretamente sem
     * ser descartada novamente.
     *
     * @param cardIndex índice da carta na mão do herói (base 0)
     * @param card      carta a ser aplicada; deve ter
     *                  {@code isMultiTarget() == true}
     */
    private void applyCardToAllEnemies(int cardIndex, Card card) {
        boolean first = true;
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                if (first) {
                    hero.useCard(cardIndex, enemy, heroDiscardPile);
                    first = false;
                } else {
                    card.useCard(hero, enemy);
                }
            }
        }
    }

    /**
     * Solicita ao jogador que escolha um inimigo vivo e aplica a carta selecionada.
     *
     * <p>
     * Se houver apenas um inimigo vivo, ele é atacado automaticamente, sem
     * necessidade de input do jogador.
     *
     * @param cardIndex índice da carta na mão do herói (base 0)
     * @param scanner   leitor de entrada do teclado; não deve ser {@code null}
     */
    private void applyCardToSingleEnemy(int cardIndex, Scanner scanner) {
        List<Enemy> aliveEnemies = enemies.stream()
                .filter(Enemy::isAlive)
                .collect(Collectors.toList());

        if (aliveEnemies.size() == 1) {
            hero.useCard(cardIndex, aliveEnemies.get(0), heroDiscardPile);
            return;
        }

        UserInterface.printEnemyTargetList(aliveEnemies);
        UserInterface.printEnemyChoicePrompt();
        int chosenEnemy = scanner.nextInt();

        if (chosenEnemy < 1 || chosenEnemy > aliveEnemies.size()) {
            UserInterface.printError("Opção inválida. Tente novamente.");
            Wait(1500);
            return;
        }

        hero.useCard(cardIndex, aliveEnemies.get(chosenEnemy - 1), heroDiscardPile);
    }

    /**
     * Executa o turno dos inimigos: cada inimigo vivo compra cartas e anuncia
     * sua estratégia para o próximo ataque.
     *
     * <p>
     * O anúncio de cada inimigo é exibido com uma pausa de {@code 2500 ms}
     * para que o jogador possa ler a intenção antes do combate.
     */
    public void enemyTurn() {
        UserInterface.clearScreen();
        UserInterface.printEnemyPlanHeader();
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                enemy.newTurn();
                UserInterface.printEnemyAnnouncement(enemy.prepareForBattle());
                Wait(2500);
            }
        }
    }

    // =========================================================================
    // Observer
    // =========================================================================

    /**
     * Dispara o evento especificado no {@link Publisher} e remove os efeitos
     * expirados da entidade originadora.
     *
     * <p>
     * Este método centraliza a integração entre o loop de combate e o padrão
     * Observer, garantindo que efeitos temporários sejam limpos após cada turno.
     *
     * @param event  identificador do evento a ser publicado (ex.:
     *               {@code "FIM_TURNO"})
     * @param user   entidade que originou o evento; seus efeitos serão gerenciados
     *               após a notificação
     * @param target entidade alvo do evento
     */
    private void notifyAndClean(String event, Entity user, Entity target) {
        publisher.notify(event, user, target);
        user.manageEffects();
    }

    // =========================================================================
    // Ponto de entrada
    // =========================================================================

    /**
     * Ponto de entrada da aplicação.
     *
     * <p>
     * Sequência de execução:
     * <ol>
     * <li>Exibe a tela de introdução e aguarda {@code 3000 ms};</li>
     * <li>Inicializa o estado do jogo via {@link #start()};</li>
     * <li>Executa o loop de batalha enquanto o herói e pelo menos um inimigo
     * estiverem vivos:
     * <ol>
     * <li>Turno dos inimigos ({@link #enemyTurn()});</li>
     * <li>Turno do herói ({@link #heroTurn(Scanner)});</li>
     * <li>Execução dos ataques dos inimigos vivos;</li>
     * <li>Notificação de fim de turno para todas as entidades.</li>
     * </ol>
     * </li>
     * <li>Exibe a tela de fim de jogo e aguarda {@code 10000 ms}.</li>
     * </ol>
     *
     * @param args argumentos de linha de comando (não utilizados)
     * @throws Exception se ocorrer erro inesperado durante a execução
     */
    public static void main(String[] args) throws Exception {
        App.gameIntro();
        Wait(3000);

        Scanner scanner = new Scanner(System.in);
        App app = new App();
        app.start();

        while (app.hero.isAlive() && app.enemies.stream().anyMatch(Enemy::isAlive) && app.currentNode != null) {
            app.enemyTurn();
            app.heroTurn(scanner);

            for (Enemy enemy : app.enemies) {
                if (enemy.isAlive()) {
                    enemy.resetShield();
                    UserInterface.clearScreen();
                    UserInterface.printEnemyAttackHeader(enemy.getName());
                    app.printCombatState();
                    enemy.executeEnemyStrategy(app.hero);
                    Wait(2500);
                }
            }

            app.notifyAndClean("FIM_TURNO", app.hero, app.enemies.get(0));
            for (Enemy enemy : app.enemies) {
                app.notifyAndClean("FIM_TURNO", enemy, app.hero);
            }

            if (!app.enemies.stream().anyMatch(Enemy::isAlive) && app.currentNode != null) {
                UserInterface.printFaseClear(app.currentNode);
                Wait(2000);

                if (app.currentNode.getLeftNode() == null && app.currentNode.getRightNode() == null) {
                    app.currentNode = null;
                    continue;
                } else if (app.currentNode.getLeftNode() == null) {
                    app.startNewFase(app.currentNode, false);
                } else if (app.currentNode.getRightNode() == null) {
                    app.startNewFase(app.currentNode, true);
                } else {
                    int choice = scanner.nextInt();
                    boolean isGoingLeft = (choice == 1) ? true : false;
                    app.startNewFase(app.currentNode, isGoingLeft);
                }
            }
        }

        // Coleta os nomes dos inimigos para a tela de fim de jogo
        List<String> enemyNames = app.enemies.stream()
                .map(Enemy::getName)
                .collect(Collectors.toList());

        UserInterface.clearScreen();
        UserInterface.printGameOver(app.hero.isAlive(), app.hero.getName(), enemyNames);

        Wait(10000);
        scanner.close();
    }
}
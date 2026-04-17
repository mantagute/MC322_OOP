package gameOrchestrator;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import gamePath.Node;
import gamePath.TreePath;
import deck.BuyPile;
import deck.DiscardPile;
import entities.Enemy;
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

    /**
     * Constrói uma nova instância de {@code App}.
     * Inicializa a lista de inimigos e o Publisher central do sistema Observer.
     */
    public App() {}

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


    // =========================================================================
    // Inicialização
    // =========================================================================

    /**
     * Inicializa o estado do jogo: instancia o herói, constrói a árvore
     * de progressão, monta e embaralha o baralho do herói, e carrega
     * os inimigos do nó raiz.
     *
     * <p>Este método deve ser chamado exatamente uma vez antes do início
     * do loop principal em {@link #main(String[])}.
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

        loadEnemiesFromNode(currentNode);
    }

    /**
     * Avança o jogo para o próximo nó da árvore de progressão,
     * limpando os inimigos e efeitos da fase anterior e carregando
     * os inimigos do nó escolhido.
     *
     * @param currentNode  nó atual, cujos filhos representam as opções de avanço
     * @param isGoingLeft  {@code true} para avançar pelo filho esquerdo;
     *                     {@code false} para o filho direito
     */

    private void startNewFase(Node currentNode, boolean isGoingLeft) {
        enemies.clear();
        publisher.resetPublisher();
        hero.clearEffects();
        if (isGoingLeft) {
            this.currentNode = currentNode.getLeftNode();
        } else {
            this.currentNode = currentNode.getRightNode();
        }

        if (this.currentNode != null) {
            loadEnemiesFromNode(this.currentNode);
        }
    }

    /**
     * Carrega os inimigos do nó especificado na lista de inimigos ativa,
     * instanciando e inicializando cada um conforme seu tipo.
     *
     * @param node nó cujos inimigos serão carregados; não deve ser {@code null}
     */

    private void loadEnemiesFromNode(Node node) {
        for (EnemyDefinition enemyDef : node.getEnemiesDefinitions()) {
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

    /**
     * Ponto de entrada da aplicação.
     *
     * <p>Sequência de execução:
     * <ol>
     *   <li>Exibe a tela de introdução e aguarda {@code 3000 ms};</li>
     *   <li>Inicializa o estado do jogo via {@link #start()};</li>
     *   <li>Executa o loop de progressão enquanto o herói estiver vivo
     *       e houver nós no mapa:
     *     <ol>
     *       <li>Cria uma nova {@link Battle} com o estado atual;</li>
     *       <li>Executa o combate via {@link Battle#runBattle()};</li>
     *       <li>Em vitória, navega para o próximo nó do mapa;</li>
     *       <li>Em derrota, encerra o loop.</li>
     *     </ol>
     *   </li>
     *   <li>Exibe a tela de fim de jogo e aguarda {@code 10000 ms}.</li>
     * </ol>
     *
     * @param args argumentos de linha de comando (não utilizados)
     * @throws Exception se ocorrer erro inesperado durante a execução
     */
    public static void main(String[] args) throws Exception {
        App.gameIntro();
        GameUtils.Wait(3000);

        Scanner scanner = new Scanner(System.in);
        App app = new App();
        app.start();

        while (app.hero.isAlive() && app.currentNode != null) {
            Battle battle = new Battle(app.hero, app.enemies, app.publisher, scanner, app.heroBuyPile, app.heroDiscardPile);
            boolean victory = battle.runBattle();

            if (victory) {
                UserInterface.printFaseClear(app.currentNode);
                GameUtils.Wait(2000);
    
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
            else {
                break;
            }  
    }

        // Coleta os nomes dos inimigos para a tela de fim de jogo
        List<String> enemyNames = app.enemies.stream()
                .map(Enemy::getName)
                .collect(Collectors.toList());

        UserInterface.clearScreen();
        UserInterface.printGameOver(app.hero.isAlive(), app.hero.getName(), enemyNames);

        GameUtils.Wait(10000);
        scanner.close();
    }
}
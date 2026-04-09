package gameOrchestrator;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import cards.Card;
import deck.BuyPile;
import deck.DiscardPile;
import entities.Hero;
import observer.Publisher;
import entities.Enemy;
import entities.Entity;


public class Battle {
    private Hero hero;
    private List<Enemy> enemies;
    private Publisher publisher;
    private Scanner scanner;
    private BuyPile heroBuyPile;
    private DiscardPile heroDiscardPile;

    public Battle (Hero hero, List<Enemy> enemies, Publisher publisher, Scanner scanner, BuyPile heroBuyPile, DiscardPile heroDiscardPile) {
        this.hero = hero;
        this.enemies = enemies;
        this.publisher = publisher;
        this.scanner = scanner;
        this.heroBuyPile = heroBuyPile;
        this.heroDiscardPile = heroDiscardPile;
    }

    public boolean runBattle() {
        while (hero.isAlive() && enemies.stream().anyMatch(Enemy::isAlive)) {
            enemyTurn();
            heroTurn(scanner);

            for (Enemy enemy : enemies) {
                if (enemy.isAlive()) {
                    enemy.resetShield();
                    UserInterface.clearScreen();
                    UserInterface.printEnemyAttackHeader(enemy.getName());
                    enemy.executeEnemyStrategy(hero);
                    GameUtils.Wait(2500);
                }
            }

            notifyAndClean("FIM_TURNO", hero, enemies.get(0));
            for (Enemy enemy : enemies) {
                notifyAndClean("FIM_TURNO", enemy,hero);
            }
        }
        return hero.isAlive();
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
                UserInterface.printCombatState(hero, enemies);
                UserInterface.printNoEnergy(hero.getName());
                GameUtils.Wait(2000);
                isTurnOver = true;
                break;
            }

            UserInterface.printHeroTurnHeader(hero.getName());
            UserInterface.printCombatState(hero, enemies);
            UserInterface.printHand(hero, hero.getHandSize());
            UserInterface.printPassOption(hero.getHandSize() + 1);
            UserInterface.printChoicePrompt();

            int choice = scanner.nextInt();

            // Passar a vez
            if (choice == hero.getHandSize() + 1) {
                UserInterface.printHeroPass(hero.getName());
                GameUtils.Wait(1500);
                isTurnOver = true;
                continue;
            }

            // Opção fora do intervalo válido
            if (choice < 1 || choice > hero.getHandSize()) {
                UserInterface.printError("Opção inválida. Tente novamente.");
                GameUtils.Wait(1500);
                continue;
            }

            Card chosenCard = hero.getCardFromHand(choice - 1);

            // Energia insuficiente para a carta escolhida
            if (chosenCard.getEnergyCost() > hero.getEnergy()) {
                UserInterface.printWarning("Energia insuficiente para esta carta.");
                GameUtils.Wait(1500);
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

            GameUtils.Wait(1800);

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
            GameUtils.Wait(1500);
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
                GameUtils.Wait(2500);
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
}
package entities;

import java.util.Random;
import observer.Publisher;
import cards.Card;
import deck.BuyPile;
import deck.DiscardPile;

/**
 * Classe abstrata que representa um inimigo do jogo.
 * Inimigos possuem baralho e lógica de combate próprios, definindo
 * automaticamente sua estratégia a cada turno por meio de seleção
 * aleatória de cartas dentro do limite de energia disponível.
 *
 * <p>Subclasses devem implementar {@link #initializeDeck()} para popular
 * o baralho e {@link #announceEnemyStrategy()} para descrever sua intenção
 * de ataque ao jogador.
 */
public abstract class Enemy extends Entity {

    private static final int MAX_PLANNED_CARDS = 5;
    private Random rand = new Random();
    private Card[] enemyStrategy = new Card[MAX_PLANNED_CARDS];
    private BuyPile buyPile = new BuyPile();
    private DiscardPile discardPile = new DiscardPile();

    /** Publisher compartilhado para inscrição de efeitos no sistema Observer. */
    protected Publisher publisher;

    /**
     * Constrói um inimigo com os atributos base.
     *
     * @param name   nome do inimigo
     * @param health pontos de vida iniciais
     * @param energy energia máxima por turno
     */
    public Enemy(String name, double health, int energy) {
        super(name, health, energy);
    }

    /**
     * Armazena o Publisher do sistema Observer e popula o baralho do inimigo
     * chamando {@link #initializeDeck()}.
     *
     * <p>Deve ser invocado imediatamente após a instanciação do inimigo,
     * pois {@link #initializeDeck()} pode criar {@code effects.EffectCard}s
     * que precisam do Publisher para se inscrever no barramento de eventos.
     *
     * @param publisher Publisher central do jogo, compartilhado entre todos os efeitos
     */
    public void initializePublisher(Publisher publisher) {
        this.publisher = publisher;
        initializeDeck();
    }

    /**
     * Inicializa o baralho do inimigo com suas cartas características.
     * Implementado por cada subclasse com as cartas específicas do inimigo.
     */
    protected abstract void initializeDeck();

    /**
     * Adiciona uma carta à pilha de compra do inimigo.
     *
     * @param card carta a ser adicionada ao baralho
     */
    protected void addCardToBuypile(Card card) {
        buyPile.push(card);
    }

    /**
     * Embaralha a pilha de compra do inimigo.
     */
    protected void shuffleBuyPile() {
        buyPile.shuffle();
    }

    /**
     * Retorna a estratégia de cartas planejada para o turno atual.
     *
     * @return array de cartas selecionadas para executar neste turno;
     *         posições não preenchidas contêm {@code null}
     */
    protected Card[] getEnemyStrategy() {
        return enemyStrategy;
    }

    /**
     * Define aleatoriamente a estratégia de cartas para o turno,
     * respeitando o limite de energia disponível e sem repetir cartas.
     * Seleciona até {@value #MAX_PLANNED_CARDS} cartas da mão atual.
     */
    private void defineEnemyStrategy() {
        for (int index = 0; index < MAX_PLANNED_CARDS; index++) {
            enemyStrategy[index] = null;
        }
        int totalCardsDefined = 0;
        int auxCurrentEnergy = getEnergy();
        boolean[] selectedCards = new boolean[getHandSize()];

        while (totalCardsDefined < MAX_PLANNED_CARDS) {
            boolean anyAvailable = false;
            for (int i = 0; i < getHandSize(); i++) {
                if (!selectedCards[i] && getCardFromHand(i).getEnergyCost() <= auxCurrentEnergy) {
                    anyAvailable = true;
                    break;
                }
            }
            if (!anyAvailable) break;

            int index = rand.nextInt(getHandSize());
            Card card = getCardFromHand(index);
            if (!selectedCards[index] && card.getEnergyCost() <= auxCurrentEnergy) {
                enemyStrategy[totalCardsDefined] = card;
                totalCardsDefined++;
                auxCurrentEnergy -= card.getEnergyCost();
                selectedCards[index] = true;
            }
        }
    }

    /**
     * Retorna uma mensagem descrevendo a intenção de combate do inimigo no turno.
     * Implementado por subclasses com texto específico ao tipo de inimigo.
     *
     * @return string descrevendo a estratégia planejada (ex.: dano total, escudo total)
     */
    public abstract String announceEnemyStrategy();

    /**
     * Define a estratégia do turno via {@link #defineEnemyStrategy()} e
     * retorna a mensagem de anúncio ao jogador via {@link #announceEnemyStrategy()}.
     *
     * @return mensagem de anúncio da estratégia do inimigo
     */
    public String prepareForBattle() {
        defineEnemyStrategy();
        return announceEnemyStrategy();
    }

    /**
     * Executa a estratégia definida, usando cada carta planejada contra o alvo.
     * Cartas nulas (posições não preenchidas na estratégia) são ignoradas.
     *
     * @param target entidade alvo dos ataques (geralmente o herói)
     */
    public void executeEnemyStrategy(Entity target) {
        for (Card card : enemyStrategy) {
            if (card != null) {
                int index = getCardIndex(card);
                if (index != -1) {
                    useCard(getCardIndex(card), target, discardPile);
                }
            }
        }
    }

    /**
     * Inicia o novo turno do inimigo, descartando a mão atual e comprando
     * novas cartas de seu próprio baralho.
     */
    public void newTurn() {
        newTurn(buyPile, discardPile);
    }
}

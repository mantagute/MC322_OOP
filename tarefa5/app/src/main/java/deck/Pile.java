package deck;

import java.util.Random;
import cards.Card;

/**
 * Classe abstrata que representa uma coleção ordenada de cartas.
 * Fornece operações básicas de pilha como inserção, extração,
 * embaralhamento e movimentação de cartas entre pilhas.
 * Subclasses definem comportamentos específicos (compra, descarte, mão).
 */
public abstract class Pile {

    private Card[] cards;
    private int size;

    /** Capacidade máxima padrão de um baralho. */
    protected static final int MAX_DECK_SIZE = 22;

    /**
     * Constrói uma pilha com a capacidade especificada.
     *
     * @param pileSize capacidade máxima desta pilha
     */
    public Pile(int pileSize) {
        this.cards = new Card[pileSize];
        this.size = 0;
    }

    /**
     * Insere uma carta no topo da pilha, se houver espaço disponível.
     *
     * @param card carta a ser inserida
     */
    public void push(Card card) {
        if (size < cards.length) {
            cards[size] = card;
            size++;
        }
    }

    /**
     * Troca as posições de duas cartas na pilha.
     *
     * @param index1 índice da primeira carta
     * @param index2 índice da segunda carta
     */
    protected void swapCards(int index1, int index2) {
        if (index1 >= 0 && index1 < size && index2 >= 0 && index2 < size) {
            Card auxCard = cards[index1];
            cards[index1] = cards[index2];
            cards[index2] = auxCard;
        }
    }

    /**
     * Embaralha as cartas da pilha em ordem aleatória
     * utilizando o algoritmo de Fisher-Yates.
     */
    public void shuffle() {
        Random rand = new Random();
        for (int i = getSize() - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swapCards(i, j);
        }
    }

    /**
     * Remove e retorna a carta no índice especificado,
     * deslocando as demais cartas para preencher o espaço.
     *
     * @param index índice da carta a ser extraída
     * @return a carta extraída, ou {@code null} se o índice for inválido
     */
    public Card extractCard(int index) {
        if (index >= 0 && index < size) {
            Card cardToBeExtracted = cards[index];
            for (int i = index; i < size - 1; i++) {
                cards[i] = cards[i + 1];
            }
            cards[size - 1] = null;
            size--;
            return cardToBeExtracted;
        }
        return null;
    }

    /**
     * Move todas as cartas desta pilha para a pilha de destino.
     * Esta pilha fica vazia após a operação.
     *
     * @param targetPile pilha que receberá todas as cartas
     */
    public void moveAllCardsTo(Pile targetPile) {
        while (size > 0) {
            Card cardToMove = extractCard(getSize() - 1);
            targetPile.push(cardToMove);
        }
    }

    /**
     * Retorna o número atual de cartas na pilha.
     *
     * @return quantidade de cartas na pilha
     */
    public int getSize() {
        return size;
    }

    /**
     * Retorna a carta no índice especificado sem removê-la da pilha.
     *
     * @param index índice da carta desejada
     * @return a carta no índice, ou {@code null} se o índice for inválido
     */
    public Card getCard(int index) {
        if (index >= 0 && index < size) {
            return cards[index];
        }
        return null;
    }

    /**
     * Retorna um array com cópias das referências de todas as cartas da pilha.
     *
     * @return array de cartas presentes na pilha
     */
    public Card[] getCards() {
        Card[] pileCards = new Card[size];
        for (int i = 0; i < size; i++) {
            pileCards[i] = cards[i];
        }
        return pileCards;
    }
}

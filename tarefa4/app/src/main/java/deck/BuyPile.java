package deck;

import cards.Card;

/**
 * Representa a pilha de compra (baralho) de uma entidade.
 * Quando a pilha está vazia, as cartas da pilha de descarte
 * são automaticamente embaralhadas e reutilizadas.
 */
public class BuyPile extends Pile {

    /**
     * Constrói uma pilha de compra com capacidade máxima padrão.
     */
    public BuyPile() {
        super(MAX_DECK_SIZE);
    }

    /**
     * Retira e retorna a próxima carta da pilha de compra.
     * Se a pilha estiver vazia, renova-a a partir da pilha de descarte.
     *
     * @param discardPile pilha de descarte usada para renovar o baralho
     * @return a carta comprada, ou {@code null} se ambas as pilhas estiverem vazias
     */
    public Card drawCard(DiscardPile discardPile) {
        if (getSize() == 0 && discardPile.getSize() > 0) {
            renewBuyPile(discardPile);
        }
        if (getSize() > 0) {
            return extractCard(getSize() - 1);
        }
        return null;
    }

    /**
     * Renova a pilha de compra embaralhando e movendo
     * todas as cartas da pilha de descarte para cá.
     *
     * @param discardPile pilha de descarte a ser reciclada
     */
    private void renewBuyPile(DiscardPile discardPile) {
        discardPile.shuffle();
        discardPile.moveAllCardsTo(this);
    }
}


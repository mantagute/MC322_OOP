package deck;

/**
 * Representa a pilha de descarte de uma entidade.
 * Cartas utilizadas durante o turno são enviadas para cá.
 * Quando a pilha de compra esgota, esta pilha é embaralhada
 * e movida de volta para a {@link BuyPile}.
 */
public class DiscardPile extends Pile {

    /**
     * Constrói uma pilha de descarte com capacidade máxima padrão.
     */
    public DiscardPile() {
        super(MAX_DECK_SIZE);
    }
}

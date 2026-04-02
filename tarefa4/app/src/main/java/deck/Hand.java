package deck;

import cards.Card;

/**
 * Representa a mão de uma entidade — as cartas disponíveis para uso no turno atual.
 * A mão tem capacidade fixa de {@value #MAX_HAND_SIZE} cartas.
 */
public class Hand extends Pile {

    /** Número máximo de cartas que a mão pode conter. */
    public static final int MAX_HAND_SIZE = 5;

    /**
     * Constrói uma mão vazia com capacidade para {@value #MAX_HAND_SIZE} cartas.
     */
    public Hand() {
        super(MAX_HAND_SIZE);
    }

    /**
     * Retorna o menor custo em energia entre todas as cartas na mão.
     * Utilizado para verificar se a entidade ainda pode jogar alguma carta.
     *
     * @return menor custo de energia entre as cartas na mão,
     *         ou {@link Integer#MAX_VALUE} se a mão estiver vazia
     */
    public int getMinimumEnergyCost() {
        if (getSize() == 0) {
            return Integer.MAX_VALUE;
        }
        Card[] cardsInHand = getCards();
        int minEnergyCost = Integer.MAX_VALUE;
        for (Card card : cardsInHand) {
            if (card.getEnergyCost() < minEnergyCost) {
                minEnergyCost = card.getEnergyCost();
            }
        }
        return minEnergyCost;
    }
}

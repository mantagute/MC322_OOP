package entities.enemies;

import cards.Card;
import cards.ShieldCard;
import entities.Enemy;
import gameOrchestrator.Data;

/**
 * Inimigo do tipo Bzoide — focado em cartas de defesa.
 * Possui um baralho com ênfase em cartas de escudo e algumas de dano e efeitos.
 * Ao anunciar sua estratégia, exibe o escudo total planejado para o turno.
 */
public class Bzoide extends Enemy {

    /**
     * Constrói um inimigo Bzoide com os atributos especificados.
     *
     * @param name      nome do Bzoide
     * @param health    pontos de vida iniciais
     * @param energy    energia máxima por turno
     */
    public Bzoide(String name, double health, int energy) {
        super(name, health, energy);
    }

    /**
     * Anuncia o escudo total que o Bzoide planeja acumular neste turno,
     * somando o valor de todas as {@link ShieldCard} na estratégia atual.
     *
     * @return mensagem descrevendo o escudo planejado pelo Bzoide
     */
    @Override
    public String announceEnemyStrategy() {
        double totalShield = 0;
        for (Card card : getEnemyStrategy()) {
            if (card instanceof ShieldCard) {
                totalShield += card.getEffectValue();
            }
        }
        return "O bzoide, " + getName() + ", planeja usar " + totalShield + " de escudo no próximo turno!";
    }

    /**
     * Inicializa o baralho do Bzoide com cartas de dano, escudo e efeitos,
     * conforme definido em {@link Data}.
     */
    public void initializeDeck() {
        for (Card card : Data.bzoideDamageCards) addCardToBuypile(card);
        for (Card card : Data.bzoideShieldCards) addCardToBuypile(card);
        for (Card card : Data.bzoideEffectCards(publisher)) addCardToBuypile(card);
        shuffleBuyPile();
    }
}


package entities.enemies;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import cards.ShieldCard;
import deck.BuyPile;
import entities.Enemy;
import gameOrchestrator.Data;
import gameOrchestrator.Data.CardDefinition;
import gameOrchestrator.GameFactory;

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
        List<CardDefinition> bzoideCards = new ArrayList<>();
        bzoideCards.addAll(Data.bzoideDamageCardsDefinitions);
        bzoideCards.addAll(Data.bzoideShieldCardsDefinitions);
        bzoideCards.addAll(Data.bzoideEffectCardsDefinitions);
        BuyPile pile = GameFactory.createBuyPile(publisher, bzoideCards);
        for (int i = 0 ; i < pile.getSize() ; i++) {
            addCardToBuypile(pile.getCard(i));
        }
    }
}


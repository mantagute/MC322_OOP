package entities.enemies;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import cards.DamageCard;
import deck.BuyPile;
import entities.Enemy;
import gameOrchestrator.Data;
import gameOrchestrator.GameFactory;
import gameOrchestrator.Data.CardDefinition;

/**
 * Inimigo do tipo Azoide — focado em cartas de ataque.
 * Possui um baralho repleto de cartas de dano e algumas de escudo e efeitos.
 * Ao anunciar sua estratégia, exibe o dano total planejado para o turno.
 */
public class Azoide extends Enemy {

    /**
     * Constrói um inimigo Azoide com os atributos especificados.
     *
     * @param name   nome do Azoide
     * @param health pontos de vida iniciais
     * @param energy energia máxima por turno
     */
    public Azoide(String name, double health, int energy) {
        super(name, health, energy);
    }

    /**
     * Anuncia o dano total que o Azoide planeja causar neste turno,
     * somando o valor de todas as {@link DamageCard} na estratégia atual.
     *
     * @return mensagem descrevendo o dano planejado pelo Azoide
     */
    @Override
    public String announceEnemyStrategy() {
        double totalDamage = 0;
        for (Card card : getEnemyStrategy()) {
            if (card instanceof DamageCard) {
                totalDamage += card.getEffectValue();
            }
        }
        return "O azoide, " + getName() + ", planeja causar " + totalDamage + " de dano no próximo turno!";
    }

    /**
     * Inicializa o baralho do Azoide com cartas de dano, escudo e efeitos,
     * conforme definido em {@link Data}.
     */
    public void initializeDeck() {
        List<CardDefinition> azoideCards = new ArrayList<>();
        azoideCards.addAll(Data.azoideDamageCardsDefinitions);
        azoideCards.addAll(Data.azoideShieldCardsDefinitions);
        azoideCards.addAll(Data.azoideEffectCardsDefinitions);
        BuyPile pile = GameFactory.createBuyPile(publisher, azoideCards);
        for (int i = 0 ; i < pile.getSize() ; i++) {
            addCardToBuypile(pile.getCard(i));
        }
    }
}

package cards;

import effects.Effect.EffectType;
import entities.Entity;
import observer.Publisher;

/**
 * Carta que aplica um efeito de status (como Veneno ou Força) em uma entidade.
 * O alvo do efeito pode ser o próprio usuário ({@code selfTarget = true})
 * ou a entidade alvo ({@code selfTarget = false}).
 */
public class EffectCard extends Card {

    private EffectType effectType;
    private double balance;
    private Publisher publisher;
    private boolean selfTarget;

    /**
     * Constrói uma carta de efeito com os atributos especificados.
     *
     * @param name        nome da carta
     * @param energyCost  custo em energia
     * @param description descrição da carta
     * @param effectType  tipo do efeito a ser aplicado ({@link EffectType})
     * @param balance     quantidade de acúmulos do efeito a aplicar
     * @param selfTarget  {@code true} se o efeito é aplicado no próprio usuário;
     *                    {@code false} se é aplicado no alvo
     * @param publisher   instância do {@link Publisher} para registro do efeito no sistema Observer
     * @param multiTarget {@code true} se a carta atinge todos os inimigos vivos
     */
    public EffectCard(String name, int energyCost, String description,EffectType effectType, double balance,boolean selfTarget, Publisher publisher, boolean multiTarget) {
        
        super(name, energyCost, description, multiTarget);
        this.effectType = effectType;
        this.balance = balance;
        this.selfTarget = selfTarget;
        this.publisher = publisher;
    }

    /**
     * Retorna a quantidade de acúmulos do efeito que esta carta aplica.
     *
     * @return quantidade de acúmulos do efeito
     */
    @Override
    public double getEffectValue() {
        return balance;
    }

    /**
     * Aplica o efeito na entidade correta (usuário ou alvo, conforme {@code selfTarget}).
     *
     * @param user   entidade que joga a carta
     * @param target entidade alvo da carta
     */
    @Override
    public void useCard(Entity user, Entity target) {
        Entity character = selfTarget ? user : target;
        character.applyEffect(effectType, balance, publisher);
    }

    /**
     * Indica se esta carta tem como alvo o próprio usuário.
     *
     * @return {@code true} se o efeito é aplicado no usuário; {@code false} se é no alvo
     */
    @Override
    public boolean isSelfTarget() {
        return selfTarget;
    }

    /**
     * Retorna uma descrição textual do efeito que esta carta aplica.
     *
     * @return descrição do comportamento do efeito, conforme seu tipo
     */
    @Override
    public String getDetails() {
        if (effectType == EffectType.POISON) {
            return " (Reduz o HP do inimigo na quantidade de acúmulos atuais associados ao efeito.)";
        } else if (effectType == EffectType.STRENGTH) {
            return " (Multiplica dano e escudo pela quantidade de acúmulos atuais associados ao efeito.)";
        }
        return "";
    }
}

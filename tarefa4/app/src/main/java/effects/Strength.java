package effects;

import entities.Entity;
import observer.Publisher;

/**
 * Efeito de força que multiplica o dano e o escudo gerados pela entidade afetada.
 *
 * <p><b>Comportamento passivo:</b> o multiplicador é aplicado via
 * {@link Entity#applyEffectMultiplier(double)} sempre que a entidade gerar
 * dano ou escudo. O valor de multiplicação equivale à quantidade de acúmulos.
 *
 * <p><b>Decaimento:</b> no fim de cada turno, perde 0,25 de acúmulo.
 * O efeito se encerra quando os acúmulos chegam a zero.
 *
 * <p><b>Exemplo:</b> 2 acúmulos de Strength dobram o dano e o escudo gerados.
 */
public class Strength extends Effect {

    /**
     * Constrói um efeito de Strength para a entidade especificada.
     *
     * @param character entidade que receberá o bônus de força
     * @param balance   quantidade inicial de acúmulos de força
     * @param publisher Publisher para inscrição no sistema de eventos
     */
    public Strength(Entity character, double balance, Publisher publisher) {
        super("Strength", character, balance, publisher);
    }

    /**
     * Reage ao evento de fim de turno: reduz 0,25 acúmulos por turno,
     * causando decaimento gradual do efeito.
     *
     * @param event  nome do evento recebido
     * @param user   entidade cujo turno está encerrando
     * @param target alvo do evento (não utilizado por este efeito)
     */
    @Override
    public void beNotified(String event, Entity user, Entity target) {
        if (event.equals("FIM_TURNO") && getBalance() >= 1 && user == character) {
            reduceBalance(0.25);
        }
    }

    /**
     * Retorna o tipo deste efeito.
     *
     * @return {@link Effect.EffectType#STRENGTH}
     */
    @Override
    public Effect.EffectType getType() {
        return Effect.EffectType.STRENGTH;
    }
}
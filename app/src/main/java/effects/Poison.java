package effects;

import observer.Publisher;
import entities.Entity;

/**
 * Efeito de veneno que causa dano à entidade afetada no fim de cada turno.
 *
 * <p><b>Comportamento:</b> ao receber o evento {@code "FIM_TURNO"} da entidade
 * afetada, causa dano igual à quantidade atual de acúmulos e reduz 1 acúmulo.
 * O efeito se encerra automaticamente quando os acúmulos chegam a zero.
 *
 * <p><b>Exemplo:</b> 5 acúmulos de Poison causam 5 de dano no fim do turno,
 * passando para 4 acúmulos na rodada seguinte.
 */
public class Poison extends Effect {

    /**
     * Constrói um efeito de Poison para a entidade especificada.
     *
     * @param character entidade que sofrerá o efeito de veneno
     * @param balance   quantidade inicial de acúmulos de veneno
     * @param publisher Publisher para inscrição no sistema de eventos
     */
    public Poison(Entity character, double balance, Publisher publisher) {
        super("Poison", character, balance, publisher);
    }

    /**
     * Reage ao evento de fim de turno: causa dano à entidade afetada
     * igual ao número de acúmulos atuais e reduz 1 acúmulo.
     *
     * @param event  nome do evento recebido
     * @param user   entidade cujo turno está encerrando
     * @param target alvo do evento (não utilizado por este efeito)
     */
    @Override
    public void beNotified(String event, Entity user, Entity target) {
        if (event.equals("FIM_TURNO") && user == character) {
            character.receiveDamage(getBalance());
            reduceBalance(1);
        }
    }

    /**
     * Retorna o tipo deste efeito.
     *
     * @return {@link Effect.EffectType#POISON}
     */
    @Override
    public Effect.EffectType getType() {
        return Effect.EffectType.POISON;
    }
}

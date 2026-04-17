package observer;

import entities.Entity;

/**
 * Classe abstrata base para assinantes do sistema Observer do jogo.
 * Qualquer componente que precise reagir a eventos de combate (como efeitos de status)
 * deve estender esta classe e implementar {@link #beNotified(String, Entity, Entity)}.
 *
 * @see Publisher
 * @see effects.Effect
 */
public abstract class Subscriber {

    /**
     * Constrói um novo Subscriber.
     * Subclasses devem implementar {@link #beNotified(String, entities.Entity, entities.Entity)}
     * para reagir aos eventos publicados pelo {@link Publisher}.
     */
    public Subscriber() {}

    /**
     * Chamado pelo {@link Publisher} quando um evento de combate ocorre.
     * Implementações devem verificar o tipo de evento e as entidades envolvidas
     * antes de aplicar qualquer lógica.
     *
     * @param event  nome do evento ocorrido (ex.: {@code "FIM_TURNO"})
     * @param user   entidade que originou o evento
     * @param target entidade alvo do evento
     */
    public abstract void beNotified(String event, Entity user, Entity target);
}
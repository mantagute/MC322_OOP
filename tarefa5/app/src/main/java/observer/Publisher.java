package observer;

import java.util.ArrayList;
import java.util.List;
import entities.Entity;

/**
 * Gerenciador central de eventos do padrão Observer.
 * Mantém uma lista de {@link Subscriber}s e os notifica sempre que
 * um evento de combate ocorre (ex.: {@code "FIM_TURNO"}).
 *
 * <p>Efeitos de status se inscrevem automaticamente ao serem criados
 * e se desinscrevem quando seus acúmulos chegam a zero, garantindo
 * que efeitos expirados não recebam notificações desnecessárias.
 */
public class Publisher {

    private List<Subscriber> subscribers = new ArrayList<>();

    /**
     * Inscreve um subscriber para receber notificações de eventos futuros.
     *
     * @param subscriber subscriber a ser inscrito
     */
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    /**
     * Remove um subscriber da lista, impedindo que receba notificações futuras.
     *
     * @param subscriber subscriber a ser removido
     */
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * Notifica todos os subscribers inscritos sobre um evento de combate.
     * Itera sobre uma cópia da lista para evitar {@code ConcurrentModificationException}
     * caso algum subscriber se desinscreva durante a notificação.
     *
     * @param event  nome do evento ocorrido (ex.: {@code "FIM_TURNO"})
     * @param user   entidade que originou o evento
     * @param target entidade alvo do evento
     */
    public void notify(String event, Entity user, Entity target) {
        List<Subscriber> currentSubscribers = new ArrayList<>(subscribers);
        for (Subscriber subscriber : currentSubscribers) {
            subscriber.beNotified(event, user, target);
        }
    }

    public void resetPublisher() {
        subscribers.clear();
    }
}

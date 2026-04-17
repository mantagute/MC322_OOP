package effects;

import observer.Subscriber;
import observer.Publisher;
import entities.Entity;

/**
 * Classe abstrata base para efeitos de status aplicados às entidades do jogo.
 * Implementa o padrão Observer como {@link Subscriber}: ao ser criado, o efeito
 * se inscreve automaticamente no {@link Publisher} e é notificado a cada evento
 * de combate relevante (ex.: {@code "FIM_TURNO"}).
 *
 * <p>Cada efeito possui uma quantidade de <b>acúmulos</b> ({@code balance}) que
 * define sua intensidade. Quando os acúmulos chegam a zero, o efeito se desinscreve
 * automaticamente, evitando efeitos "fantasma".
 *
 * @see Poison
 * @see Strength
 */
public abstract class Effect extends Subscriber {

    private String type;
    private double balance;

    /** Instância do Publisher usada para inscrição/desinscrição do efeito. */
    protected Publisher publisher;

    /** Entidade sobre a qual este efeito está ativo. */
    protected Entity character;

    /**
     * Enumeração dos tipos de efeito disponíveis no jogo.
     */
    public enum EffectType {
        /** Veneno: causa dano ao alvo no fim de cada turno, decaindo 1 acúmulo por turno. */
        POISON,
        /** Força: multiplica o dano e escudo gerados pela entidade, decaindo 0,25 por turno. */
        STRENGTH
    }

    /**
     * Constrói um novo efeito e o inscreve automaticamente no Publisher.
     *
     * @param type      nome textual do tipo de efeito (ex.: "Poison", "Strength")
     * @param character entidade que receberá o efeito
     * @param balance   quantidade inicial de acúmulos
     * @param publisher Publisher no qual o efeito será inscrito
     */
    public Effect(String type, Entity character, double balance, Publisher publisher) {
        this.type = type;
        this.character = character;
        this.balance = balance;
        this.publisher = publisher;
        publisher.subscribe(this);
    }

    /**
     * Reduz os acúmulos do efeito pela quantidade especificada.
     * Se os acúmulos chegarem a zero ou abaixo, o efeito se desinscreve do Publisher.
     *
     * @param reduction quantidade de acúmulos a reduzir
     */
    protected void reduceBalance(double reduction) {
        balance = balance - reduction;
        if (balance <= 0) {
            publisher.unsubscribe(this);
        }
    }

    /**
     * Adiciona acúmulos ao efeito (usado ao reaplicar o mesmo efeito a uma entidade).
     *
     * @param addition quantidade de acúmulos a adicionar
     */
    public void addBalance(double addition) {
        balance = balance + addition;
    }

    /**
     * Retorna o tipo enumerado deste efeito.
     *
     * @return tipo do efeito ({@link EffectType})
     */
    public abstract EffectType getType();

    /**
     * Retorna uma representação textual do efeito e seus acúmulos atuais.
     *
     * @return string no formato {@code "Tipo -> X acúmulo(s)"}
     */
    public String getString() {
        return type + " -> " + balance + (balance == 1 ? " acúmulo." : " acúmulos.");
    }

    /**
     * Retorna a quantidade atual de acúmulos deste efeito.
     *
     * @return acúmulos atuais do efeito
     */
    public double getBalance() {
        return balance;
    }
}



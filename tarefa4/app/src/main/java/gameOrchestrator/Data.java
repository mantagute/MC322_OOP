package gameOrchestrator;

import java.util.List;

import cards.Card;
import cards.DamageCard;
import cards.EffectCard;
import cards.ShieldCard;
import deck.BuyPile;
import effects.Effect.EffectType;
import entities.Hero;
import entities.enemies.Azoide;
import entities.enemies.Bzoide;
import observer.Publisher;

/**
 * Repositório central de dados estáticos do jogo.
 *
 * <p>Contém as definições de todas as cartas do herói e dos inimigos,
 * a lista de heróis disponíveis e métodos utilitários para criação
 * de entidades e preenchimento de baralhos.
 *
 * <p>As listas de cartas são imutáveis ({@link List#of}) e compartilhadas
 * entre instâncias — nunca devem ser modificadas em tempo de execução.
 * Cartas de efeito ({@link EffectCard}) são criadas sob demanda via métodos,
 * pois precisam de uma instância de {@link Publisher} para se inscrever
 * no sistema Observer.
 *
 * @see cards.DamageCard
 * @see cards.ShieldCard
 * @see cards.EffectCard
 */
public class Data {

    // =========================================================================
    // Cartas do Azoide
    // =========================================================================

    /**
     * Cartas de dano do inimigo Azoide.
     * Focadas em ataques diretos com valores variados de dano.
     */
    public static final List<DamageCard> azoideDamageCards = List.of(
        new DamageCard("Cachoeira",          5, 25, "Água que vem de cima, desce com força.", false),
        new DamageCard("Porrada",            3, 15, "Quem ficar parado vai tomar um tá ligado.", false),
        new DamageCard("Berimbau",          10, 50, "O molho paraense.", false),
        new DamageCard("Cusparada",          5, 25, "Salve-se quem puder.", false),
        new DamageCard("Ataque de Búfalo",   2, 10, "Manada no horizonte.", false),
        new DamageCard("Futebol Arte",       8, 40, "O bola de ouro de Curioutinga.", false),
        new DamageCard("Biquinho",           1,  5, "Pequeno, mas dói.", false),
        new DamageCard("Terror Bicolor",    10, 50, "Paysanduuuuuu.", false),
        new DamageCard("Continência",        5, 35, "Cria dos milicos!", false),
        new DamageCard("Açaí com Leite",   5, 25, "O sabOOr paraense.", false),
        new DamageCard("Freiras Paraenses Arretadas",       10, 50, "Ninguém segura esse grupinho...", false),
        new DamageCard("Camiseta de Rock",           3,  15, "Led Zeppelin, Iron Maiden ou AC/DC... Pode escolher.", false),
        new DamageCard("Casaco do Harry Potter",    8, 40, "Viajou para a Europa, já sabe.", false),
        new DamageCard("New Balance Marrom",        10, 50, "Pau para toda obra.", false),
        new DamageCard("Respirada Profunda", 2, 10, "Ela sempre está lá...", false)
    );

    /**
     * Cartas de escudo do inimigo Azoide.
     * Representam itens e traços característicos da personalidade paraense.
     */
    public static final List<ShieldCard> azoideShieldCards = List.of(
        new ShieldCard("Charme",                        1,  5, "Às vezes só o charme já resolve.", false),
        new ShieldCard("Xampu Clear Men",               5, 25, "Caspa? Aqui não.", false),
        new ShieldCard("Macbook Rosinha",               2, 10, "Entusiasta do ecossistema Apple.", false),
        new ShieldCard("COP 30",                        5, 25, "Viva o Pará.", false),
        new ShieldCard("Pingente The Last of Us",       5, 25, "Todo mundo tem seu lado gamer.", false)
    );

    /**
     * Cria e retorna as cartas de efeito do inimigo Azoide.
     *
     * <p>Criadas sob demanda pois precisam de um {@link Publisher} para
     * inscrever os efeitos no sistema Observer.
     *
     * @param publisher instância do Publisher compartilhado do jogo
     * @return lista imutável com as cartas de efeito do Azoide
     */
    public static final List<EffectCard> azoideEffectCards(Publisher publisher) {
        return List.of(
            new EffectCard("Ancestrais Paraenses", 3, "Todo o know-how do interior invocado",
                    EffectType.STRENGTH, 2, true, publisher, false),
            new EffectCard("AET", 1, "Cuidado para não se contaminar...",
                    EffectType.POISON, 10, false, publisher, false)
        );
    }

    // =========================================================================
    // Cartas do Bzoide
    // =========================================================================

    /**
     * Cartas de dano do inimigo Bzoide.
     * Focadas em ataques variados com referências à cultura curitibana.
     */
    public static final List<DamageCard> bzoideDamageCards = List.of(
        new DamageCard("Bomba do Ninja",          8, 40, "Silencioso, mas mortal.", false),
        new DamageCard("Tesão, piá",              1,  5, "A gíria dos guri.", false),
        new DamageCard("Cabritinho",              7, 35, "Ninguém esperava.", false),
        new DamageCard("Rolê de Bike",            5, 25, "Rumo ao Iron Man.", false),
        new DamageCard("Robozinhos da Phoenix",   5, 35, "Bzoide na veia.", false)
    );

    /**
     * Cartas de escudo do inimigo Bzoide.
     * Representam itens e hábitos característicos do estilo de vida curitibano.
     */
    public static final List<ShieldCard> bzoideShieldCards = List.of(
        new ShieldCard("Erudição",               2, 10, "Saber é poder.", false),
        new ShieldCard("Celular na Boca",        10, 50, "Maxilar quadrado ativado.", false),
        new ShieldCard("Capacetinho",             3, 15, "Melhor prevenir do que remediar.", false),
        new ShieldCard("Banquinho de Banheiro",   5, 25, "Conforto em primeiro lugar.", false),
        new ShieldCard("Jeans Apertadinha",       3, 15, "Estilo é para poucos.", false),
        new ShieldCard("Dry-Fit",                 1,  5, "Pau para toda obra.", false),
        new ShieldCard("Peita de Handebol",       4, 20, "01, não tem jeito.", false),
        new ShieldCard("Ilha do Mel",             7, 35, "Descansar, ninguém é de ferro.", false),
        new ShieldCard("Fúria Independente",      8, 40, "Esperando a volta do Paraná Clube.", false),
        new ShieldCard("99Food",                 10, 50, "Delivery no precinho.", false),
        new ShieldCard("Marcação do Handebol",   10, 50, "Cachorro louco.", false),
        new ShieldCard("Rolê no São Lourenço",    5, 35, "O melhor parque de Cwb.", false),
        new ShieldCard("Arremesso do Handebol",   8, 40, "Com ou sem efeito?", false),
        new ShieldCard("Questão de MecG",         2, 10, "Trivial.", false)
    );

    /**
     * Cria e retorna as cartas de efeito do inimigo Bzoide.
     *
     * <p>Criadas sob demanda pois precisam de um {@link Publisher} para
     * inscrever os efeitos no sistema Observer.
     *
     * @param publisher instância do Publisher compartilhado do jogo
     * @return lista imutável com as cartas de efeito do Bzoide
     */
    public static final List<EffectCard> bzoideEffectCards(Publisher publisher) {
        return List.of(
            new EffectCard("Girl", 3, "Na frente dela, não pode passar vergonha",
                    EffectType.STRENGTH, 2, true, publisher, false),
            new EffectCard("Curitiba way of life", 1, "Não brinque com os sulistas...",
                    EffectType.POISON, 10, false, publisher, true),
            new EffectCard("Caipirinha de Morango", 1, "Doce por fora, letal por dentro.",
                    EffectType.POISON, 10, false, publisher, true)       
        );
    }

    // =========================================================================
    // Cartas do Herói
    // =========================================================================

    /**
     * Cartas de dano do herói Didi Marco.
     * Incluem cartas de alvo único e cartas multi-alvo ({@code multiTarget = true}).
     */
    public static final List<DamageCard> heroDamageCards = List.of(
        new DamageCard("Erudição",            10, 50, "O conhecimento é a maior arma.", false),
        new DamageCard("Lock In",              5, 25, "Foco total, sem distrações.", false),
        new DamageCard("Macaca",               5, 35, "A ponte é a maior de Campinas.", true),
        new DamageCard("Rolê de Abarth",       8, 40, "0 a 100 em 7 segundos.", false),
        new DamageCard("Bola de 3",            2, 10, "Apenas shuá.", false),
        new DamageCard("Meia Cano Alto",       8, 40, "Drip.", false),
        new DamageCard("Colégio Rio Branco",   1,  5, "Elite intelectual.", false),
        new DamageCard("400K Dol de Earnings", 7, 35, "3 FNCS na conta.", false),
        new DamageCard("Aura",                10, 50, "Presença que paralisa.", true),
        new DamageCard("Festa do Didi",        5, 35, "Todo mundo quer estar, poucos são chamados.", true)
    );

    /**
     * Cartas de escudo do herói Didi Marco.
     * Representam itens e traços do estilo icônico do personagem.
     */
    public static final List<ShieldCard> heroShieldCards = List.of(
        new ShieldCard("Brinquinho de Diamante",  2, 10, "Drip.", false),
        new ShieldCard("Chunky Vans",            10, 50, "Para poucos.", false),
        new ShieldCard("Boné Cabuloso",           3, 15, "Esse não vende em qualquer lugar...", false),
        new ShieldCard("Show de Trap",            5, 25, "É a 30 no comando.", false),
        new ShieldCard("Baggy Jeans",             3, 15, "Conforto e estilo em um só.", false),
        new ShieldCard("BerCalça",                1,  5, "Poucos saberão distinguir.", false),
        new ShieldCard("Cabelo Descolorido",      4, 20, "NEVOU!", false),
        new ShieldCard("Dono da Bola",            7, 35, "Sem ele, ninguém joga.", false),
        new ShieldCard("Chains",                  8, 40, "Ouro no pescoço, armadura no espírito.", false),
        new ShieldCard("Birkin",                 10, 50, "Exclusividade que protege.", false)
    );

    /**
     * Cria e retorna as cartas de efeito do herói Didi Marco.
     *
     * <p>Criadas sob demanda pois precisam de um {@link Publisher} para
     * inscrever os efeitos no sistema Observer.
     *
     * @param publisher instância do Publisher compartilhado do jogo
     * @return lista imutável com as cartas de efeito do herói
     */
    public static final List<EffectCard> heroEffectCards(Publisher publisher) {
        return List.of(
            new EffectCard("The one, the only.", 3, "É o tal do 01",
                    EffectType.STRENGTH, 2, true, publisher, false),
            new EffectCard("Moggar", 1, "Não está sobrando nada...",
                    EffectType.POISON, 10, false, publisher, true)
        );
    }

    // =========================================================================
    // Heróis e fábrica de inimigos
    // =========================================================================

    /**
     * Lista de heróis disponíveis no jogo.
     * Atualmente contém apenas o herói Didi Marco.
     */
    public static final List<Hero> heroes = List.of(
        new Hero("Didi Marco", 200, 10)
    );

    /**
     * Cria e retorna uma nova instância de {@link Azoide} com os atributos fornecidos.
     *
     * @param name      nome do inimigo
     * @param health    pontos de vida iniciais
     * @param energy    energia máxima por turno
     * @param publisher Publisher para inscrição de efeitos
     * @return nova instância de Azoide
     */
    public static final Azoide createAzoide(String name, double health, int energy, Publisher publisher) {
        return new Azoide(name, health, energy, publisher);
    }

    /**
     * Cria e retorna uma nova instância de {@link Bzoide} com os atributos fornecidos.
     *
     * @param name      nome do inimigo
     * @param health    pontos de vida iniciais
     * @param energy    energia máxima por turno
     * @param publisher Publisher para inscrição de efeitos
     * @return nova instância de Bzoide
     */
    public static final Bzoide createBzoide(String name, double health, int energy, Publisher publisher) {
        return new Bzoide(name, health, energy, publisher);
    }

    // =========================================================================
    // Utilitários
    // =========================================================================

    /**
     * Preenche uma {@link BuyPile} com todas as cartas da lista fornecida.
     * Utilizado durante a inicialização do baralho do herói em {@link App#start()}.
     *
     * @param buyPile pilha de compra a ser preenchida
     * @param cards   lista de cartas a inserir na pilha
     */
    public static void fillPile(BuyPile buyPile, List<? extends Card> cards) {
        for (Card card : cards) {
            buyPile.push(card);
        }
    }
}

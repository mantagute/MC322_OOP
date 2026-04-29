package gameOrchestrator;

import java.util.List;

import cards.Card;
import cards.DamageCard;
import cards.EffectCard;
import cards.ShieldCard;
import deck.BuyPile;
import effects.Effect.EffectType;
import entities.Hero;
import events.Battle;
import events.CampFire;
import events.Choice;
import events.choice.DamageOption;
import events.choice.HealOption;
import observer.Publisher;
import events.Event;
import events.Shop;


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

    /**
     * Classe utilitária de dados — não deve ser instanciada.
     */
    private Data() {}

    public static final List<CardDefinition> shopDamageCardsDefinitions = List.of(
        new CardDefinition("IFGW", 10, 50, "O Filho chora e a mae nao ve", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("JIU JITSU UNICAMP", 8, 40, "OSS", CardDefinition.CardType.DAMAGE, false, false, null)
    );

    public static final List<CardDefinition> shopShieldCardsDefinitions = List.of(
        new CardDefinition("IC FAPESP", 10, 50, "Aquele milao para viver como playboy", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("RS", 8, 40, "Sem fila, com vento e dignidade!", CardDefinition.CardType.SHIELD, false, false, null)
    );

    // =========================================================================
    // Cartas do Azoide
    // =========================================================================

    /**
     * Cartas de dano do inimigo Azoide.
     * Focadas em ataques diretos com valores variados de dano.
     */
    public static final List<CardDefinition> azoideDamageCardsDefinitions = List.of(
        new CardDefinition("Cachoeira",          5, 25, "Água que vem de cima, desce com força.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Porrada",            3, 15, "Quem ficar parado vai tomar um tá ligado.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Berimbau",          10, 50, "O molho paraense.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Cusparada",          5, 25, "Salve-se quem puder.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Ataque de Búfalo",   2, 10, "Manada no horizonte.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Futebol Arte",       8, 40, "O bola de ouro de Curioutinga.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Biquinho",           1,  5, "Pequeno, mas dói.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Terror Bicolor",    10, 50, "Paysanduuuuuu.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Continência",        5, 35, "Cria dos milicos!", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Açaí com Leite",   5, 25, "O sabOOr paraense.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Freiras Paraenses Arretadas",       10, 50, "Ninguém segura esse grupinho...", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Camiseta de Rock",           3,  15, "Led Zeppelin, Iron Maiden ou AC/DC... Pode escolher.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Casaco do Harry Potter",    8, 40, "Viajou para a Europa, já sabe.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("New Balance Marrom",        10, 50, "Pau para toda obra.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Respirada Profunda", 2, 10, "Ela sempre está lá...", CardDefinition.CardType.DAMAGE, false, false, null)
    );

    /**
     * Cartas de escudo do inimigo Azoide.
     * Representam itens e traços característicos da personalidade paraense.
     */
    public static final List<CardDefinition> azoideShieldCardDefinitions = List.of(
        new CardDefinition("Charme",                        1,  5, "Às vezes só o charme já resolve.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Xampu Clear Men",               5, 25, "Caspa? Aqui não.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Macbook Rosinha",               2, 10, "Entusiasta do ecossistema Apple.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("COP 30",                        5, 25, "Viva o Pará.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Pingente The Last of Us",       5, 25, "Todo mundo tem seu lado gamer.", CardDefinition.CardType.SHIELD, false, false, null)
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
    public static final List<CardDefinition> azoideEffectCardsDefinitions = List.of(
        new CardDefinition("Ancestrais Paraenses", 3,  2, "Todo o know-how do interior invocado", CardDefinition.CardType.EFFECT, false, true,  EffectType.STRENGTH),
        new CardDefinition("AET",                  1, 10, "Cuidado para não se contaminar...",    CardDefinition.CardType.EFFECT, false, false, EffectType.POISON)
    );

    // =========================================================================
    // Cartas do Bzoide
    // =========================================================================

    /**
     * Cartas de dano do inimigo Bzoide.
     * Focadas em ataques variados com referências à cultura curitibana.
     */
    public static final List<CardDefinition> bzoideDamageCardsDefinitions = List.of(
        new CardDefinition("Bomba do Ninja",          8, 40, "Silencioso, mas mortal.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Tesão, piá",              1,  5, "A gíria dos guri.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Cabritinho",              7, 35, "Ninguém esperava.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Rolê de Bike",            5, 25, "Rumo ao Iron Man.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Robozinhos da Phoenix",   5, 35, "Bzoide na veia.", CardDefinition.CardType.DAMAGE, false, false, null)
    );

    /**
     * Cartas de escudo do inimigo Bzoide.
     * Representam itens e hábitos característicos do estilo de vida curitibano.
     */
    public static final List<CardDefinition> bzoideShieldCardsDefinitions = List.of(
        new CardDefinition("Erudição",               2, 10, "Saber é poder.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Celular na Boca",        10, 50, "Maxilar quadrado ativado.",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Capacetinho",             3, 15, "Melhor prevenir do que remediar.",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Banquinho de Banheiro",   5, 25, "Conforto em primeiro lugar.",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Jeans Apertadinha",       3, 15, "Estilo é para poucos.",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Dry-Fit",                 1,  5, "Pau para toda obra.",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Peita de Handebol",       4, 20, "01, não tem jeito.",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Ilha do Mel",             7, 35, "Descansar, ninguém é de ferro.",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Fúria Independente",      8, 40, "Esperando a volta do Paraná Clube.",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("99Food",                 10, 50, "Delivery no precinho.",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Marcação do Handebol",   10, 50, "Cachorro louco.",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Rolê no São Lourenço",    5, 35, "O melhor parque de Cwb.",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Arremesso do Handebol",   8, 40, "Com ou sem efeito?",  CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Questão de MecG",         2, 10, "Trivial.",  CardDefinition.CardType.SHIELD, false, false, null)
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
    public static final List<CardDefinition> bzoideEffectCardsDefinitions = List.of(
        new CardDefinition("Girl",                  3,  2, "Na frente dela, não pode passar vergonha", CardDefinition.CardType.EFFECT, false, true,  EffectType.STRENGTH),
        new CardDefinition("Curitiba way of life",  1, 10, "Não brinque com os sulistas...",           CardDefinition.CardType.EFFECT, true,  false, EffectType.POISON),
        new CardDefinition("Caipirinha de Morango", 1, 10, "Doce por fora, letal por dentro.",         CardDefinition.CardType.EFFECT, true,  false, EffectType.POISON)
    );

    // =========================================================================
    // Cartas do Herói
    // =========================================================================

    /**
     * Cartas de dano do herói Didi Marco.
     * Incluem cartas de alvo único e cartas multi-alvo ({@code multiTarget = true}).
     */
    public static final List<CardDefinition> heroDamageCardsDefinitions = List.of(
        new CardDefinition("Erudição",            10, 50, "O conhecimento é a maior arma.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Lock In",              5, 25, "Foco total, sem distrações.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Macaca",               5, 35, "A ponte é a maior de Campinas.", CardDefinition.CardType.DAMAGE, true, false, null),
        new CardDefinition("Rolê de Abarth",       8, 40, "0 a 100 em 7 segundos.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Bola de 3",            2, 10, "Apenas shuá.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Meia Cano Alto",       8, 40, "Drip.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Colégio Rio Branco",   1,  5, "Elite intelectual.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("400K Dol de Earnings", 7, 35, "3 FNCS na conta.", CardDefinition.CardType.DAMAGE, false, false, null),
        new CardDefinition("Aura",                10, 50, "Presença que paralisa.", CardDefinition.CardType.DAMAGE, true, false, null),
        new CardDefinition("Festa do Didi",        5, 35, "Todo mundo quer estar, poucos são chamados.", CardDefinition.CardType.DAMAGE, true, false, null)
    );

    /**
     * Cartas de escudo do herói Didi Marco.
     * Representam itens e traços do estilo icônico do personagem.
     */
    public static final List<CardDefinition> heroShieldCardsDefinitions = List.of(
        new CardDefinition("Brinquinho de Diamante",  2, 10, "Drip.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Chunky Vans",            10, 50, "Para poucos.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Boné Cabuloso",           3, 15, "Esse não vende em qualquer lugar...", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Show de Trap",            5, 25, "É a 30 no comando.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Baggy Jeans",             3, 15, "Conforto e estilo em um só.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("BerCalça",                1,  5, "Poucos saberão distinguir.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Cabelo Descolorido",      4, 20, "NEVOU!", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Dono da Bola",            7, 35, "Sem ele, ninguém joga.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Chains",                  8, 40, "Ouro no pescoço, armadura no espírito.", CardDefinition.CardType.SHIELD, false, false, null),
        new CardDefinition("Birkin",                 10, 50, "Exclusividade que protege.", CardDefinition.CardType.SHIELD, false, false, null)
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
    public static final List<CardDefinition> heroEffectCardsDefinitions = List.of(
        new CardDefinition("The one, the only.", 3, 2,  "É o tal do 01",             CardDefinition.CardType.EFFECT, false, true,  EffectType.STRENGTH),
        new CardDefinition("Moggar",             1, 10, "Não está sobrando nada...", CardDefinition.CardType.EFFECT, true,  false, EffectType.POISON)
    );

    /**
     * Lista de grupos de inimigos usada para construir a árvore de progressão.
     * Cada elemento representa os inimigos de um nó do mapa.
     * O elemento central torna-se a raiz; os demais formam as subárvores.
     */

    public static final List<List<EnemyDefinition>> enemies = List.of(
        List.of(new EnemyDefinition("Sr. Doutor Cabo Arruda", 100, 10, EnemyDefinition.EnemyType.AZOIDE), new EnemyDefinition("3L", 100, 10, EnemyDefinition.EnemyType.BZOIDE)),
        List.of(new EnemyDefinition("Sinhô Jelado", 50, 10, EnemyDefinition.EnemyType.AZOIDE), new EnemyDefinition("Lucas, o Ético", 50, 10, EnemyDefinition.EnemyType.BZOIDE)),
        List.of(new EnemyDefinition("Kojak, o que promete", 25, 10, EnemyDefinition.EnemyType.AZOIDE), new EnemyDefinition("Yugo, o Furtivo", 25, 10, EnemyDefinition.EnemyType.BZOIDE)),
        List.of(new EnemyDefinition("Marquinhos, o Loiro", 100, 10, EnemyDefinition.EnemyType.AZOIDE), new EnemyDefinition("Cairê, o Belo", 100, 10, EnemyDefinition.EnemyType.BZOIDE)),
        List.of(new EnemyDefinition("Adobe, o Insociável", 50, 10, EnemyDefinition.EnemyType.AZOIDE), new EnemyDefinition("O Inominável", 50, 10, EnemyDefinition.EnemyType.BZOIDE))
    );
    
    /**
     * Define um inimigo com seus atributos e tipo.
     *
     * @param name   nome do inimigo
     * @param health pontos de vida iniciais
     * @param energy energia máxima por turno
     * @param type   tipo do inimigo ({@link EnemyType})
     */
    public record EnemyDefinition(String name, double health, int energy, EnemyType type) {
    /**
     * Representa os tipos de inimigos disponíveis.
     */
    public enum EnemyType { 
            /** Inimigo do tipo Azoide. */
            AZOIDE, 
            /** Inimigo do tipo Bzoide. */
            BZOIDE 
        }
    }


    public record CardDefinition(
        String name,
        int energyCost,
        double effectValue,
        String description,
        CardType type,
        boolean multiTarget,
        boolean selfTarget,
        EffectType effectType
    ) {
        public enum CardType { 
            DAMAGE, 
            SHIELD, 
            EFFECT 
        }
    }

    public static final List<Choice> choices = List.of(
        new Choice(
            "Você encontra uma barraca de açaí na beira do caminho. O vendedor te oferece um copo extra grande com leite condensado e morango. Parece irrecusável...",
            List.of(
                new HealOption("Recusar educadamente.", "Aquilo era terra SABOOR açaí, fugiu de uma dor de barriga."),
                new DamageOption("Deliciar-se com a iguaria paraense.", "Não aceite açaí duvidoso no rolê, cuidado fella.")
            )
        ),
        new Choice(
            "Um bixo te pede ajuda com uma questão de MecG.",
            List.of(
                new HealOption("Tentar resolver.", "Computeiro que é computeiro não treme na base. A questão era fácil, você farmou aquela AURA."),
                new DamageOption("Ignorar e seguir em frente.", "O bixão virou teu chefe e você ficou na saudade...")
            )
        ),
        new Choice(
            "Você encontra o Cabo Arruda dormindo em uma rede. Ele murmura algo sobre experimentos. Você pode acordá-lo ou deixá-lo dormir.",
            List.of(
                new HealOption("Deixar dormir.", "Cada kchorro... a tal da sesta é o motor maior do alto desempenho."),
                new DamageOption("Acordar.", "Não se mexe com viking paraense e suas erudições, Cabo Arruda te PUNIU.")
            )
        )
    );

    public static final List<List<Event>> nodes = List.of(
        List.<Event>of(
            choices.get(0),
            new Battle(List.of(new EnemyDefinition("Sr. Doutor Cabo Arruda", 100, 10, EnemyDefinition.EnemyType.AZOIDE), new EnemyDefinition("3L", 100, 10, EnemyDefinition.EnemyType.BZOIDE)))
        ),
        List.<Event>of(
            new Shop()
        ),
        List.<Event>of(
            new Battle(List.of(new EnemyDefinition("Sinhô Jelado", 50, 10, EnemyDefinition.EnemyType.AZOIDE), new EnemyDefinition("Lucas, o Ético", 50, 10, EnemyDefinition.EnemyType.BZOIDE)))
        ),
        List.<Event>of(
            choices.get(1),
            new Battle(List.of(new EnemyDefinition("Kojak, o que promete", 25, 10, EnemyDefinition.EnemyType.AZOIDE), new EnemyDefinition("Yugo, o Furtivo", 25, 10, EnemyDefinition.EnemyType.BZOIDE)))
        ),
        List.<Event>of(
            choices.get(2),
            new Battle(List.of(new EnemyDefinition("Marquinhos, o Loiro", 100, 10, EnemyDefinition.EnemyType.AZOIDE), new EnemyDefinition("Cairê, o Belo", 100, 10, EnemyDefinition.EnemyType.BZOIDE)))
        ),
        List.<Event>of(
            new CampFire()
        ),
        List.<Event>of(
            new Battle(List.of(new EnemyDefinition("Adobe, o Insociável", 50, 10, EnemyDefinition.EnemyType.AZOIDE), new EnemyDefinition("O Inominável", 50, 10, EnemyDefinition.EnemyType.BZOIDE)))
        )
    );
    // =========================================================================
    // Heróis e fábrica de inimigos
    // =========================================================================

    public record HeroDefinition(String name, int health, int energy) {}
    public static final HeroDefinition heroDefinition = new HeroDefinition("Didi Marco", 200, 10);
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

    /**
     * Busca e retorna uma carta específica com base no seu nome.
     * <p>
     * O método procura a carta sequencialmente nas coleções de cartas de dano, 
     * cartas de escudo e, por fim, nas cartas de efeito associadas ao editor (publisher) fornecido. 
     * A primeira carta encontrada cujo nome corresponda exatamente ao parâmetro será retornada.
     * </p>
     *
     * @param name      O nome exato da carta a ser buscada.
     * @param publisher O editor ({@code Publisher}) utilizado para obter a coleção de cartas de efeito específicas.
     * @return A instância de {@code Card} correspondente ao nome procurado, ou {@code null} caso a carta não seja encontrada em nenhuma das coleções.
     */
    public static Card getCardbyName(String name, Publisher publisher) {
        for (Card card : heroDamageCards) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        for (Card card : heroShieldCards) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        for (Card card : heroEffectCards(publisher)) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }
}

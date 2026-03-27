import java.util.List;

import cards.DamageCard;
import cards.Card;
import cards.ShieldCard;
import cards.EffectCard;
import deck.BuyPile;
import effects.Effect.EffectType;
import entities.Hero;
import entities.enemies.Azoide;
import entities.enemies.Bzoide;
import observer.Publisher;

public class Data {
    
    public static final List<DamageCard> azoideDamageCards = List.of(
        new DamageCard("Cachoeira",         5, 25, "Água que vem de cima, desce com força."),
        new DamageCard("Porrada",           3, 15, "Quem ficar parado vai tomar um tá ligado."),
        new DamageCard("Berimbau",         10, 50, "O molho paraense."),
        new DamageCard("Cusparada",         5, 25, "Salve-se quem puder."),
        new DamageCard("Ataque de Búfalo",  2, 10, "Manada no horizonte."),
        new DamageCard("Futebol Arte",      8, 40, "O bola de ouro de Curioutinga."),
        new DamageCard("Biquinho",          1,  5, "Pequeno, mas dói."),
        new DamageCard("Terror Bicolor",   10, 50, "Paysanduuuuuu."),
        new DamageCard("Continência",       5, 35, "Cria dos milicos!"),
        new DamageCard("Respirada Profunda",2, 10, "Ela sempre está lá...")
    );

    public static final List<ShieldCard> azoideShieldCards = List.of(
        new ShieldCard("Açaí com Leite",              5, 25, "O sabOOr paraense."),
        new ShieldCard("Freiras Paraenses Arretadas", 10, 50, "Ninguém segura esse grupinho..."),
        new ShieldCard("Camiseta de Rock",             3, 15, "Led Zeppelin, Iron Maiden ou AC/DC... Pode escolher."),
        new ShieldCard("Casaco do Harry Potter",       8, 40, "Viajou para a Europa, já sabe."),
        new ShieldCard("New Balance Marrom",          10, 50, "Pau para toda obra."),
        new ShieldCard("Charme",                       1,  5, "Às vezes só o charme já resolve."),
        new ShieldCard("Xampu Clear Men",              5, 25, "Caspa? Aqui não."),
        new ShieldCard("Macbook Rosinha",              2, 10, "Entusiasta do ecossistema Apple."),
        new ShieldCard("COP 30",                       5, 25, "Viva o Pará."),
        new ShieldCard("Pingente The Last of Us",      5, 25, "Todo mundo tem seu lado gamer.")
    );

    public static final List<DamageCard> bzoideDamageCards = List.of(
        new DamageCard("Marcação do Handebol",  10, 50, "Cachorro louco."),
        new DamageCard("Caipirinha de Morango",  5, 25, "Doce por fora, letal por dentro."),
        new DamageCard("Rolê no São Lourenço",   5, 35, "O melhor parque de Cwb."),
        new DamageCard("Arremesso do Handebol",  8, 40, "Com ou sem efeito?"),
        new DamageCard("Questão de MecG",        2, 10, "Trivial."),
        new DamageCard("Bomba do Ninja",         8, 40, "Silencioso, mas mortal."),
        new DamageCard("Tesão, piá",             1,  5, "A gíria dos guri."),
        new DamageCard("Cabritinho",             7, 35, "Ninguém esperava."),
        new DamageCard("Rolê de Bike",           5, 25, "Rumo ao Iron Man."),
        new DamageCard("Robozinhos da Phoenix",  5, 35, "Bzoide na veia.")
    );

    public static final List<ShieldCard> bzoideShieldCards = List.of(
        new ShieldCard("Erudição",              2, 10, "Saber é poder."),
        new ShieldCard("Celular na Boca",      10, 50, "Maxilar quadrado ativado."),
        new ShieldCard("Capacetinho",           3, 15, "Melhor prevenir do que remediar."),
        new ShieldCard("Banquinho de Banheiro", 5, 25, "Conforto em primeiro lugar."),
        new ShieldCard("Jeans Apertadinha",     3, 15, "Estilo é para poucos."),
        new ShieldCard("Dry-Fit",               1,  5, "Pau para toda obra."),
        new ShieldCard("Peita de Handebol",     4, 20, "01, não tem jeito."),
        new ShieldCard("Ilha do Mel",           7, 35, "Descansar, ninguém é de ferro."),
        new ShieldCard("Fúria Independente",    8, 40, "Esperando a volta do Paraná Clube."),
        new ShieldCard("99Food",               10, 50, "Delivery no precinho.")
    );

    public static final List<DamageCard> heroDamageCards = List.of(
        new DamageCard("Erudição",           10, 50, "O conhecimento é a maior arma."),
        new DamageCard("Lock In",             5, 25, "Foco total, sem distrações."),
        new DamageCard("Macaca",              5, 35, "A ponte é a maior de Campinas."),
        new DamageCard("Rolê de Abarth",      8, 40, "0 a 100 em 7 segundos."),
        new DamageCard("Bola de 3",           2, 10, "Apenas shuá."),
        new DamageCard("Meia Cano Alto",      8, 40, "Drip."),
        new DamageCard("Colégio Rio Branco",  1,  5, "Elite intelectual."),
        new DamageCard("400K Dol de Earnings",7, 35, "3 FNCS na conta."),
        new DamageCard("Aura",               10, 50, "Presença que paralisa."),
        new DamageCard("Festa do Didi",       5, 35, "Todo mundo quer estar, poucos são chamados.")
    );

    public static final List<ShieldCard> heroShieldCards = List.of(
        new ShieldCard("Brinquinho de Diamante", 2, 10, "Drip."),
        new ShieldCard("Chunky Vans",           10, 50, "Para poucos."),
        new ShieldCard("Boné Cabuloso",          3, 15, "Esse não vende em qualquer lugar..."),
        new ShieldCard("Show de Trap",           5, 25, "É a 30 no comando."),
        new ShieldCard("Baggy Jeans",            3, 15, "Conforto e estilo em um só."),
        new ShieldCard("BerCalça",               1,  5, "Poucos saberão distinguir."),
        new ShieldCard("Cabelo Descolorido",     4, 20, "NEVOU!"),
        new ShieldCard("Dono da Bola",           7, 35, "Sem ele, ninguém joga."),
        new ShieldCard("Chains",                 8, 40, "Ouro no pescoço, armadura no espírito."),
        new ShieldCard("Birkin",                10, 50, "Exclusividade que protege.")
    );

    public static final List<EffectCard> heroEffectCards(Publisher publisher) {
        return List.of(
            new EffectCard("The one, the only.", 3, "É o tal do 01", EffectType.STRENGTH, 2, true, publisher),
            new EffectCard("Moggar", 1, "Não está sobrando nada...", EffectType.POISON, 10, false, publisher)
        );
    }

    public static final List<EffectCard> azoideEffectCards(Publisher publisher) {
        return List.of(
            new EffectCard("Ancestrais Paraenses", 3, "Todo o know-how do interior invocado", EffectType.STRENGTH, 2, true, publisher),
            new EffectCard("AET", 1, "Cuidado para não se contaminar...", EffectType.POISON, 10, false, publisher)
        );
    }

    public static final List<EffectCard> bzoideEffectCards(Publisher publisher) {
        return List.of(
            new EffectCard("Girl", 3, "Na frente dela, não pode passar vergonha", EffectType.STRENGTH, 2, true, publisher),
            new EffectCard("Curitiba way of life", 1, "Não brinque com os sulistas...", EffectType.POISON, 10, false, publisher)
        );
    }

    public static final List <Hero> heroes = List.of(
        new Hero("Didi Marco",100, 10)
    );

    public static final List <Azoide> azoides = List.of(
        new Azoide("Sr. Doutor Cabo Arruda", 100, 10)
    );

    public static final List <Bzoide> bzoides = List.of(
        new Bzoide("3L",100, 10)
    );

    public static void fillPile(BuyPile buyPile, List<? extends Card> cards) {
        for (Card card : cards) {
            buyPile.push(card);
        }
    }
}

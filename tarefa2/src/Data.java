import java.util.List;

import cards.DamageCard;
import cards.ShieldCard;
import entities.Hero;
import entities.enemies.Azoide;
import entities.enemies.Bzoide;

public class Data {
    
    public static final List <DamageCard> azoideDamageCards = List.of(
        new DamageCard("Cachoeira",5, 25, "" ),
        new DamageCard("Porrada", 3, 15, "" ),
        new DamageCard("Berimbau", 10, 50, "" ),
        new DamageCard("Cusparada",5, 25, "" ),
        new DamageCard("Ataque de Búfalo", 2, 10, "" ),
        new DamageCard("Futebol arte", 8, 40, "" ),
        new DamageCard("Biquinho",1, 5, "" ),
        new DamageCard("Terror Bicolor",10, 50, "" ),
        new DamageCard("Continencia",5, 35, "" ),
        new DamageCard("Respirada profunda",2, 10, "" )
    );

    public static final List <ShieldCard> azoideShieldCards = List.of(
        new ShieldCard("Açaí com Leite",5, 25, "" ),
        new ShieldCard("Freiras Paraenses Arretadas",10, 50, "" ),
        new ShieldCard("Camiseta de Rock",3, 15, "" ),
        new ShieldCard("Casaco do Harry Potter",8, 40, "" ),
        new ShieldCard("New Balance Marrom",10, 50, "" ),
        new ShieldCard("Charme",1, 5, "" ),
        new ShieldCard("Xampu Clear Men",5, 25, "" ),
        new ShieldCard("Macbook rosinha",2, 10, "" ),
        new ShieldCard("COP 30",5, 25, "" ),
        new ShieldCard("Pingente The Last of Us",5, 25, "" )
    );

    public static final List <DamageCard> bzoideDamageCards = List.of(
        new DamageCard("Marcação do Handebol",10, 50, "" ),
        new DamageCard("Caipirinha de Morango",5, 25, "" ),
        new DamageCard("Role no São Lourenço",5, 35, "" ),
        new DamageCard("Arremesso do Handebol",8, 40, "" ),
        new DamageCard("Questão de MecG",2, 10, "" ),
        new DamageCard("Bomba do Ninja",8, 40, "" ),
        new DamageCard("Tesão, piá",1, 5, "" ),
        new DamageCard("Cabritinho",7, 35, "" ),
        new DamageCard("Role de Bike",5, 25, "" ),
        new DamageCard("Robozinhos da Phoenix",5, 35, "" )
    );

    public static final List <ShieldCard> bzoideShieldCards = List.of(
        new ShieldCard("Erudicao",2, 10,  "" ),
        new ShieldCard("Celular na boca",10, 50,  "" ),
        new ShieldCard("Capacetinho",3, 15,  "" ),
        new ShieldCard("Banquinho de banheiro",5, 25,  "" ),
        new ShieldCard("Jeans apertadinha",3, 15,  "" ),
        new ShieldCard("Dry-Fit",1, 5,  "" ),
        new ShieldCard("Peita de Handebol",4, 20,  "" ),
        new ShieldCard("Ilha do Mel",7, 35,  "" ),
        new ShieldCard("Fúria Independente",8, 40,  "" ),
        new ShieldCard("99Food",10, 50,  "" )
    );

    public static final List <DamageCard> heroDamageCards = List.of(
        new DamageCard("Erudicao",10, 50, "" ),
        new DamageCard("Lock in",5, 25, "" ),
        new DamageCard("Macaca",5, 35, "" ),
        new DamageCard("Role de Abarth",8, 40, "" ),
        new DamageCard("Bola de 3",2, 10, "" ),
        new DamageCard("Meia cano alto",8, 40, "" ),
        new DamageCard("Colégio Rio Brancoi",1, 5, "" ),
        new DamageCard("400K dol de earnings",7, 35, "" ),
        new DamageCard("Aura",10, 50, "" ),
        new DamageCard("Festa do didi",5, 35, "" )
    );

    public static final List <ShieldCard> heroShieldCards = List.of(
        new ShieldCard("Brinquinho de diamante",2, 10,  "" ),
        new ShieldCard("Chunky Vans",10, 50,  "" ),
        new ShieldCard("Boné Cabuloso",3, 15,  "" ),
        new ShieldCard("Show de Trap",5, 25,  "" ),
        new ShieldCard("Baggy Jeans",3, 15,  "" ),
        new ShieldCard("BerCalça",1, 5,  "" ),
        new ShieldCard("Cabelo descolorido",4, 20,  "" ),
        new ShieldCard("Dono da bola",7, 35,  "" ),
        new ShieldCard("Chains",8, 40,  "" ),
        new ShieldCard("Birkin",10, 50,  "" )
    );

    public static final List <Hero> heros = List.of(
        new Hero("Didi Marco",100, 10)
    );

    public static final List <Azoide> azoides = List.of(
        new Azoide("Sr. Doutor Cabo Arruda", 100, 10)
    );

    public static final List <Bzoide> bzoides = List.of(
        new Bzoide("3L",100, 10)
    );

    
}

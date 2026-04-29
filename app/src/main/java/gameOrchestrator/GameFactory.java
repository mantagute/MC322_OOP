package gameOrchestrator;

import gameOrchestrator.Data.CardDefinition;
import gameOrchestrator.Data.HeroDefinition;
import cards.DamageCard;
import cards.EffectCard;
import cards.ShieldCard;
import deck.BuyPile;
import cards.Card;
import entities.Hero;
import observer.Publisher;


public class GameFactory {
    
    public static Hero createHero() {
        HeroDefinition def = Data.heroDefinition;
        return new Hero(def.name(), def.health(), def.energy());
    }

    public static Card createCardFromDefinition(CardDefinition def, Publisher publisher) {
    switch (def.type()) {
        case DAMAGE:
            return new DamageCard(def.name(), def.energyCost(), def.effectValue(), def.description(), def.multiTarget());
        case SHIELD:
            return new ShieldCard(def.name(), def.energyCost(), def.effectValue(), def.description(), def.multiTarget());
        case EFFECT:
            return new EffectCard(def.name(), def.energyCost(), def.description(), def.effectType(), def.effectValue(), def.selfTarget(), publisher, def.multiTarget());
        default:
            throw new IllegalArgumentException("Unknown card type: " + def.type());
    }
    }

    public static BuyPile createHeroBuyPile(Publisher publisher) {
        BuyPile buyPile = new BuyPile();
        for (CardDefinition def : Data.heroDamageCardsDefinitions) {
            buyPile.push(createCardFromDefinition(def, publisher));
        }
        for (CardDefinition def : Data.heroShieldCardsDefinitions) {
            buyPile.push(createCardFromDefinition(def, publisher));
        }
        for (CardDefinition def : Data.heroEffectCardsDefinitions) {
            buyPile.push(createCardFromDefinition(def, publisher));
        }
        buyPile.shuffle();
        return buyPile;
    }
}

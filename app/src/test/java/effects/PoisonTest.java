package effects;

import entities.Hero;
import effects.Effect.EffectType;
import observer.Publisher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PoisonTest {

    @Test
    public void PoisonEffectReducesHealth() {
        Hero hero = new Hero("Envenenado", 100, 10);
        Publisher publisher = new Publisher();
        hero.applyEffect(EffectType.POISON, 10, publisher);
        publisher.notify("FIM_TURNO", hero, hero);
        assertEquals(90, hero.getHealth());
    }

    @Test 
    public void PoisonEffectReducesOneBalance() {
        Hero hero = new Hero("Enveneado" , 100, 10);
        Publisher publisher = new Publisher();
        hero.applyEffect(EffectType.POISON, 10, publisher);
        publisher.notify("FIM_TURNO", hero, hero);
        assertEquals(90, hero.getHealth());
        publisher.notify("FIM_TURNO", hero, hero);
        assertEquals(81, hero.getHealth());
    }

    @Test
    public void PoisonOnlyAffectsEntityItWasAppliedTo() {
        Hero hero1 = new Hero("Envenenado", 100,10);
        Hero hero2 = new Hero("Envenenado", 100,10);
        Publisher publisher = new Publisher();
        hero1.applyEffect(EffectType.POISON, 10, publisher);
        publisher.notify("FIM_TURNO", hero2, hero2);
        assertEquals(100, hero1.getHealth());
    }
}

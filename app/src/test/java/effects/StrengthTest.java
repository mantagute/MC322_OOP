package effects;

import entities.Hero;
import observer.Publisher;

import org.junit.jupiter.api.Test;

import effects.Effect.EffectType;

import static org.junit.jupiter.api.Assertions.*;


public class StrengthTest {

    @Test
    public void StrengthEffectIncreasesDamage() {
        Hero hero = new Hero("Turbinado", 100, 10);
        Publisher publisher = new Publisher();
        hero.applyEffect(EffectType.STRENGTH, 2, publisher);
        publisher.notify("FIM_TURNO", hero, hero);
        assertEquals(17.5, hero.applyEffectMultiplier(10));     
    }
}

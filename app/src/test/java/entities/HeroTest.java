package entities;


import observer.Publisher;
import effects.Effect.EffectType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeroTest {

    @Test
    public void startAlive() {
        Hero hero = new Hero("Tá Vivao?",100, 10);
        assertTrue(hero.isAlive());
    }

    @Test
    public void DamageAbsorbedByShield() {
        Hero hero = new Hero("Escudo tankou", 100, 10);
        hero.receiveShield(10);
        hero.receiveDamage(8);
        assertEquals(2, hero.getShield());
        assertEquals(100, hero.getHealth());
    }

    @Test
    public void DamageExceedsShield() {
        Hero hero = new Hero("Tomou no HP", 100, 10);
        hero.receiveShield(10);
        hero.receiveDamage(15);
        assertEquals(0, hero.getShield());
        assertEquals(95, hero.getHealth());
    }

    @Test
    public void DamageInHealth() {
        Hero hero = new Hero("Só no HP", 100, 10);
        hero.receiveDamage(50);
        assertEquals(50,hero.getHealth());
    }

    @Test 
    public void StrengthActivated() {
        Publisher publisher = new Publisher();
        Hero hero = new Hero("TURBINADO", 100, 10);
        hero.applyEffect(EffectType.STRENGTH, 2, publisher);
        assertEquals(20, hero.applyEffectMultiplier(10));
    }

    @Test
    public void HeroDies() {
        Hero hero = new Hero("Foi de berco", 100, 10);
        hero.receiveDamage(150);
        assertFalse(hero.isAlive());
    }


}

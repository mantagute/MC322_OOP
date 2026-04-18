package entities.enemies;

import observer.Publisher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class AzoideTest {

    @Test 
    public void isAliveAtStart() {
        Azoide azoide = new Azoide("tá vivo", 100, 10);
        assertTrue(azoide.isAlive());
    }

    @Test
    public void isAliveAfterFatalDamage() {
        Azoide azoide = new Azoide("vai morrer", 100, 10);
        azoide.receiveDamage(150);
        assertFalse(azoide.isAlive());
    }

    @Test 
    public void verifyAnnouncementEstrategy() {
        Azoide azoide = new Azoide("VAI ANUNCIAR", 100, 10);
        Publisher publisher = new Publisher();
        azoide.initializePublisher(publisher);
        azoide.prepareForBattle();
        assertNotNull(azoide.announceEnemyStrategy());
    }

}

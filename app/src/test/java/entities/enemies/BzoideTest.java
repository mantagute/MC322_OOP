package entities.enemies;

import observer.Publisher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BzoideTest {

    @Test 
    public void isAliveAtStart() {
        Bzoide bzoide = new Bzoide("tá vivo", 100, 10);
        assertTrue(bzoide.isAlive());
    }

    @Test
    public void isAliveAfterFatalDamage() {
        Bzoide bzoide = new Bzoide("vai morrer", 100, 10);
        bzoide.receiveDamage(150);
        assertFalse(bzoide.isAlive());
    }

    @Test 
    public void verifyAnnouncementEstrategy() {
        Bzoide bzoide = new Bzoide("VAI ANUNCIAR", 100, 10);
        Publisher publisher = new Publisher();
        bzoide.initializePublisher(publisher);
        bzoide.prepareForBattle();
        assertNotNull(bzoide.announceEnemyStrategy());
    }
}


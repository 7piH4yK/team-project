package entity.clickable_objects;

import entity.ClickableObject;
import entity.ClickableObjectFactory;
import entity.Collectibles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClickableObjectFactoryTest {

    @Test
    void testCreateClickableObject() {
        ClickableObjectFactory factory = new ClickableObjectFactory();

        ClickableObject obj = factory.create("Tree", 100, 200, "tree.png");

        assertNotNull(obj);
        assertEquals("Tree", obj.getName());
        assertEquals(100, obj.getCoordinateX());
        assertEquals(200, obj.getCoordinateY());
        assertEquals("tree.png", obj.getImage());
    }

    @Test
    void testCreateCollectibles() {
        ClickableObjectFactory factory = new ClickableObjectFactory();

        Collectibles c = factory.createCollectibles("Sword", 10, 20, "sword.png");

        assertNotNull(c);
        assertEquals("Sword", c.getName());
        assertEquals(10, c.getCoordinateX());
        assertEquals(20, c.getCoordinateY());
        assertEquals("sword.png", c.getImage());
        assertTrue(c instanceof Collectibles);
    }

    @Test
    void testFactoryProducesDifferentObjects() {
        ClickableObjectFactory factory = new ClickableObjectFactory();

        ClickableObject obj = factory.create("Rock", 4, 5, "rock.png");
        Collectibles col = factory.createCollectibles("Gem", 6, 7, "gem.png");

        assertFalse(obj instanceof Collectibles);
        assertTrue(col instanceof Collectibles);
    }
}

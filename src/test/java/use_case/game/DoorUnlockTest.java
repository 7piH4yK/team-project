package use_case.game;

import entity.ClickableObject;
import entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class DoorUnlockTest {

    private Player player;
    private HashSet<String> unlocked;

    @BeforeEach
    public void setup() {
        player = new Player();
        unlocked = new HashSet<>();
    }

    private boolean isDoorUnlocked(String name) {
        return unlocked.contains(name);
    }

    private void unlockDoor(String name) {
        unlocked.add(name);
    }

    @Test
    public void testDoorCannotBeOpenedWithoutKey() {
        assertFalse(isDoorUnlocked("Object2"));
        assertFalse(player.hasItemNamed("Object3"));
    }

    @Test
    public void testDoorOpensWithKey() {
        ClickableObject key = new ClickableObject("Object3", 0,0,"key.png", true);

        // Collect key
        player.addToInventory(key);
        assertTrue(player.hasItemNamed("Object3"));

        // Use key to unlock door
        unlockDoor("Object2");

        // Remove key from inventory
        player.removeItemNamed("Object3");

        assertTrue(isDoorUnlocked("Object2"));
        assertFalse(player.hasItemNamed("Object3"));  // key consumed
    }

    @Test
    public void testDoorStaysUnlockedAfterUse() {
        unlockDoor("Object2");

        assertTrue(isDoorUnlocked("Object2"));

        // Even with no key
        assertFalse(player.hasItemNamed("Object3"));
        assertTrue(isDoorUnlocked("Object2"));
    }

    @Test
    public void testKeyConsumption() {
        ClickableObject key = new ClickableObject("Object3", 0,0,"key.png", true);

        player.addToInventory(key);
        assertTrue(player.hasItemNamed("Object3"));

        // consume
        player.removeItemNamed("Object3");

        assertFalse(player.hasItemNamed("Object3"));
    }
}

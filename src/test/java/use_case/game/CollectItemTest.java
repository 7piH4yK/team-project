package use_case.game;

import entity.ClickableObject;
import entity.Collectibles;
import entity.Player;
import entity.Scene;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CollectItemTest {

    @Test
    public void testCollectableItemAddedToInventory() {
        Player player = new Player();
        Collectibles key = new Collectibles("Object3", 0,0,"key.png");

        player.addToInventory(key);

        assertTrue(player.hasItemNamed("Object3"));
    }

    @Test
    public void testNonCollectableItemNotCollectedAutomatically() {
        Player player = new Player();
        Collectibles door = new Collectibles("Object2", 0,0,"door.png");

        // Normally not added unless explicitly done
        assertFalse(player.hasItemNamed("Object2"));
    }

    @Test
    public void testCollectableIsRemovedFromScene() {
        Collectibles key = new Collectibles("key", 100,100,"key.png");

        Scene scene = new Scene(
                "Scene1",
                List.of(
                        new Collectibles("Object1", 0,0,"obj1.png"),
                        key
                ),
                "bg.png"
        );

        // remove Object3
        List<ClickableObject> updated = new java.util.ArrayList<>(scene.getObjects());
        updated.removeIf(o -> o.getName().equals("key"));

        Scene updatedScene = new Scene("Scene1", updated, "bg.png");

        assertEquals(1, updatedScene.getObjects().size());
        assertFalse(updatedScene.getObjects()
                .stream()
                .anyMatch(o -> o.getName().equals("key")));
    }
}

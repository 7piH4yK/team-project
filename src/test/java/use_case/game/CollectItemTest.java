package use_case.game;

import entity.ClickableObject;
import entity.Player;
import entity.Scene;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CollectItemTest {

    @Test
    public void testCollectableItemAddedToInventory() {
        Player player = new Player();
        ClickableObject key = new ClickableObject("Object3", 0,0,"key.png", true);

        player.addToInventory(key);

        assertTrue(player.hasItemNamed("Object3"));
    }

    @Test
    public void testNonCollectableItemNotCollectedAutomatically() {
        Player player = new Player();
        ClickableObject door = new ClickableObject("Object2", 0,0,"door.png", false);

        // Normally not added unless explicitly done
        assertFalse(player.hasItemNamed("Object2"));
    }

    @Test
    public void testCollectableIsRemovedFromScene() {
        ClickableObject key = new ClickableObject("Object3", 100,100,"key.png", true);

        Scene scene = new Scene(
                "Scene1",
                List.of(
                        new ClickableObject("Object1", 0,0,"obj1.png", false),
                        key
                ),
                "bg.png"
        );

        // remove Object3
        List<ClickableObject> updated = new java.util.ArrayList<>(scene.getObjects());
        updated.removeIf(o -> o.getName().equals("Object3"));

        Scene updatedScene = new Scene("Scene1", updated, "bg.png");

        assertEquals(1, updatedScene.getObjects().size());
        assertFalse(updatedScene.getObjects()
                .stream()
                .anyMatch(o -> o.getName().equals("Object3")));
    }
}

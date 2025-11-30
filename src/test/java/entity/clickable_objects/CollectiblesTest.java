package entity.clickable_objects;

import entity.ClickableObject;
import entity.Collectibles;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollectiblesTest {

    @Test
    void testValidConstructor() {
        Collectibles c = new Collectibles("Coin", 12, 34, "coin.png");

        assertEquals("Coin", c.getName());
        assertEquals(12, c.getCoordinateX());
        assertEquals(34, c.getCoordinateY());
        assertEquals("coin.png", c.getImage());
    }

    @Test
    void testEmptyNameThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Collectibles("", 0, 0, "img.png"));
    }

    @Test
    void testEmptyImageThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Collectibles("Object", 0, 0, ""));
    }

    @Test
    void testToJson() {
        Collectibles c = new Collectibles("Gem", 10, 20, "gem.png");
        JSONObject json = c.toJson();

        assertEquals("Gem", json.getString("name"));
        assertEquals(10, json.getInt("coordinateX"));
        assertEquals(20, json.getInt("coordinateY"));
        assertEquals("gem.png", json.getString("image"));
    }

    @Test
    void testFromJson() {
        JSONObject json = new JSONObject();
        json.put("name", "Potion");
        json.put("coordinateX", 3);
        json.put("coordinateY", 4);
        json.put("image", "potion.png");

        Collectibles c = Collectibles.fromJson(json);

        assertEquals("Potion", c.getName());
        assertEquals(3, c.getCoordinateX());
        assertEquals(4, c.getCoordinateY());
        assertEquals("potion.png", c.getImage());
    }

    @Test
    void testJsonRoundTrip() {
        Collectibles original = new Collectibles("Ring", 9, 9, "ring.png");
        JSONObject json = original.toJson();
        Collectibles restored = Collectibles.fromJson(json);

        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getCoordinateX(), restored.getCoordinateX());
        assertEquals(original.getCoordinateY(), restored.getCoordinateY());
        assertEquals(original.getImage(), restored.getImage());
    }

    @Test
    void testIsSubclass() {
        Collectibles c = new Collectibles("Book", 1, 1, "book.png");
        assertTrue(c instanceof ClickableObject);
    }
}

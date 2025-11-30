package entity.clickable_objects;

import entity.ClickableObject;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClickableObjectTest {

    @Test
    void testConstructorAndGetters() {
        ClickableObject obj = new ClickableObject("Key", 10, 20, "key.png");

        assertEquals("Key", obj.getName());
        assertEquals(10, obj.getCoordinateX());
        assertEquals(20, obj.getCoordinateY());
        assertEquals("key.png", obj.getImage());
    }

    @Test
    void testToJson() {
        ClickableObject obj = new ClickableObject("Key", 10, 20, "key.png");
        JSONObject json = obj.toJson();

        assertEquals("Key", json.getString("name"));
        assertEquals(10, json.getInt("coordinateX"));
        assertEquals(20, json.getInt("coordinateY"));
        assertEquals("key.png", json.getString("image"));
    }

    @Test
    void testFromJson() {
        JSONObject json = new JSONObject();
        json.put("name", "Potion");
        json.put("coordinateX", 5);
        json.put("coordinateY", 15);
        json.put("image", "potion.png");

        ClickableObject obj = ClickableObject.fromJson(json);

        assertEquals("Potion", obj.getName());
        assertEquals(5, obj.getCoordinateX());
        assertEquals(15, obj.getCoordinateY());
        assertEquals("potion.png", obj.getImage());
    }

    @Test
    void testJsonRoundTrip() {
        ClickableObject original = new ClickableObject("Gem", 3, 7, "gem.png");
        JSONObject json = original.toJson();
        ClickableObject restored = ClickableObject.fromJson(json);

        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getCoordinateX(), restored.getCoordinateX());
        assertEquals(original.getCoordinateY(), restored.getCoordinateY());
        assertEquals(original.getImage(), restored.getImage());
    }
}

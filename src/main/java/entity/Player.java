package entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player in the game with an inventory.
 */
public class Player {

    private final List<Collectibles> inventory;

    /**
     * Creates a new player with an empty inventory.
     */
    public Player() {
        this.inventory = new ArrayList<>();
    }

    /**
     * Creates a new player with the given inventory.
     * @param inventory the initial inventory
     */
    public Player(List<Collectibles> inventory) {
        this.inventory = new ArrayList<>(inventory);
    }

    public List<Collectibles> getInventory() {
        return new ArrayList<>(inventory);
    }

    /**
     * Adds an object to the player's inventory.
     * @param object the object to add
     */
    public void addToInventory(Collectibles object) {
        inventory.add(object);
    }

    /**
     * Removes an object from the player's inventory.
     * @param object the object to remove
     */
    public void removeFromInventory(Collectibles object) {
        inventory.remove(object);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray inventoryArray = new JSONArray();

        for (Collectibles obj : inventory) {
            inventoryArray.put(obj.toJson());
        }

        json.put("inventory", inventoryArray);
        return json;
    }

    public static Player fromJson(JSONObject json) {
        JSONArray invArray = json.getJSONArray("inventory");
        List<Collectibles> inventory = new ArrayList<>();
        for (int i = 0; i < invArray.length(); i++) {
            inventory.add(Collectibles.fromJson(invArray.getJSONObject(i)));
        }
        return new Player(inventory);
    }
    public boolean hasItemNamed(String name) {
        for (Collectibles obj : inventory) {
            if (obj.getName().equals(name)) return true;
        }
        return false;
    }
    public void removeItemNamed(String name) {
        inventory.removeIf(obj -> obj.getName().equals(name));
    }

    /** Optional: returns the first item with this name, or null if none. */
    public Collectibles findItemNamed(String name) {
        for (Collectibles obj : inventory) {
            if (obj.getName().equals(name)) return obj;
        }
        return null;
    }


}

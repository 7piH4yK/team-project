package entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player in the game with an inventory.
 */
public class Player {

    private final List<ClickableObject> inventory;

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
    public Player(List<ClickableObject> inventory) {
        this.inventory = new ArrayList<>(inventory);
    }

    public List<ClickableObject> getInventory() {
        return new ArrayList<>(inventory);
    }

    /**
     * Adds an object to the player's inventory.
     * @param object the object to add
     */
    public void addToInventory(ClickableObject object) {
        inventory.add(object);
    }

    /**
     * Removes an object from the player's inventory.
     * @param object the object to remove
     */
    public void removeFromInventory(ClickableObject object) {
        inventory.remove(object);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray inventoryArray = new JSONArray();

        for (ClickableObject obj : inventory) {
            inventoryArray.put(obj.toJson());
        }

        json.put("inventory", inventoryArray);
        return json;
    }

    public static Player fromJson(JSONObject json) {
        JSONArray invArray = json.getJSONArray("inventory");
        List<ClickableObject> inventory = new ArrayList<>();
        for (int i = 0; i < invArray.length(); i++) {
            inventory.add(ClickableObject.fromJson(invArray.getJSONObject(i)));
        }
        return new Player(inventory);
    }
    public boolean hasItemNamed(String name) {
        for (ClickableObject obj : inventory) {
            if (obj.getName().equals(name)) return true;
        }
        return false;
    }
    public boolean removeItemNamed(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(name)) {
                inventory.remove(i);
                return true;
            }
        }
        return false;
    }

    /** Optional: returns the first item with this name, or null if none. */
    public ClickableObject findItemNamed(String name) {
        for (ClickableObject obj : inventory) {
            if (obj.getName().equals(name)) return obj;
        }
        return null;
    }


}

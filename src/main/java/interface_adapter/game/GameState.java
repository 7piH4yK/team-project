package interface_adapter.game;

import entity.ClickableObject;
import java.util.List;

public class GameState {
    private String backgroundImage;
    private List<ClickableObject> clickableObjects;

    // NEW — inventory items for the UI (image + name)
    private List<ClickableObject> inventoryItems;

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public List<ClickableObject> getClickableObjects() {
        return clickableObjects;
    }

    public void setClickableObjects(List<ClickableObject> clickableObjects) {
        this.clickableObjects = clickableObjects;
    }

    // NEW GETTER/SETTER
    public List<ClickableObject> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<ClickableObject> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}

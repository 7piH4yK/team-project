package interface_adapter.game;

import entity.ClickableObject;

import java.util.List;

/**
 * The state for the Game View Model.
 */
public class GameState {
    private String backgroundImage;
    private List<ClickableObject> clickableObjects;
    private DialogueBox currentDialogue;
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

    public List<ClickableObject> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<ClickableObject> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}

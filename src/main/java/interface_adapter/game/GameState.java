package interface_adapter.game;

import entity.ClickableObject;
import entity.Collectibles;
import entity.DialogueBox;

import java.util.List;

/**
 * The state for the Game View Model.
 */
public class GameState {
    private String backgroundImage;
    private List<ClickableObject> clickables;
    private List<Collectibles> inventoryItems;

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public List<ClickableObject> getClickableObjects() {
        return clickables;
    }

    public void setClickableObjects(List<ClickableObject> collectibles) {
        this.clickables = collectibles;
    }

    public List<Collectibles> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<Collectibles> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}

package interface_adapter.game;

import entity.ClickableObject;
import entity.Collectibles;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Game View Model.
 */
public class GameState {
    private String backgroundImage;
    private List<ClickableObject> clickables;
    private List<Collectibles> inventoryItems;
    private String sceneName;

    public String getSceneName() { return sceneName; }
    public void setSceneName(String sceneName) { this.sceneName = sceneName; }

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
        return inventoryItems == null ? new ArrayList<>() : inventoryItems;
    }


    public void setInventoryItems(List<Collectibles> inventoryItems) {
        this.inventoryItems = (inventoryItems == null ? new ArrayList<>() : inventoryItems);
    }



}

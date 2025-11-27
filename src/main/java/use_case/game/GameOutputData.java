package use_case.game;

import entity.ClickableObject;
import entity.Collectibles;

import java.util.List;

/**
 * Output data for the Game use case.
 */
public class GameOutputData {

    private String backgroundImage;
    private List<ClickableObject> clickables;

    private List<Collectibles> inventory;

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

    public List<Collectibles> getInventory() {
        return inventory;
    }

    public void setInventory(List<Collectibles> inventory) {
        this.inventory = inventory;
    }
}

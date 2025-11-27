package use_case.load;

import entity.ClickableObject;
import entity.Collectibles;

import java.util.List;

public class LoadOutputData {
    private String backgroundImage;
    private List<ClickableObject> clickableObjects;
    private List<Collectibles> inventory;

    public List<Collectibles> getInventory() {
        return inventory;
    }

    public void setInventory(List<Collectibles> inventory) {
        this.inventory = inventory;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public List<ClickableObject> getClickableObjects() {
        return clickableObjects;
    }

    public void setClickableObjects(List<ClickableObject> collectibles) {
        this.clickableObjects = collectibles;
    }
}

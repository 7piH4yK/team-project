package use_case.game;

import entity.ClickableObject;
import java.util.List;

public class GameOutputData {
    private String backgroundImage;
    private List<ClickableObject> clickableObjects;

    // NEW
    private List<ClickableObject> inventory;

    public String getBackgroundImage() { return backgroundImage; }
    public void setBackgroundImage(String backgroundImage) { this.backgroundImage = backgroundImage; }

    public List<ClickableObject> getClickableObjects() { return clickableObjects; }
    public void setClickableObjects(List<ClickableObject> clickableObjects) { this.clickableObjects = clickableObjects; }

    // NEW GETTER/SETTER
    public List<ClickableObject> getInventory() { return inventory; }
    public void setInventory(List<ClickableObject> inventory) { this.inventory = inventory; }
}

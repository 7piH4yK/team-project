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
    private List<ClickableObject> clickable;
    private DialogueBox currentDialogue;
    private List<Collectibles> inventoryItems = new java.util.ArrayList<>();

    private String sceneName;

    public String getSceneName() { return sceneName; }
    public void setSceneName(String name) { this.sceneName = name; }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public List<ClickableObject> getClickableObjects() {
        return clickable;
    }

    public void setClickableObjects(List<ClickableObject> collectibles) {
        this.clickable = collectibles;
    }

    public DialogueBox getCurrentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogue(DialogueBox currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    public List<Collectibles> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<Collectibles> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }


}

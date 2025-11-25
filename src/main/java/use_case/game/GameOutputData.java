package use_case.game;

import entity.ClickableObject;
import entity.Collectibles;
import entity.DialogueBox;

import java.util.List;

/**
 * Output data for the Game use case.
 */
public class GameOutputData {

    private String backgroundImage;
    private List<ClickableObject> clickable;
    private DialogueBox currentDialogue;

    private String sceneName;

    private List<Collectibles> inventory;

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
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

    public List<Collectibles> getInventory() {
        return inventory;
    }

    public void setInventory(List<Collectibles> inventory) {
        this.inventory = inventory;
    }
}

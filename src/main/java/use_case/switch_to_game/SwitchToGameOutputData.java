package use_case.switch_to_game;

import entity.ClickableObject;
import entity.DialogueBox;

import java.util.List;

/**
 * Output data for the Switch to Game use case.
 */
public class SwitchToGameOutputData {

    private String backgroundImage;
    private List<ClickableObject> clickableObjects;
    private DialogueBox currentDialogue;

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

    public DialogueBox getCurrentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogue(DialogueBox currentDialogue) {
        this.currentDialogue = currentDialogue;
    }
}

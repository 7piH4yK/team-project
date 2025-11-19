package use_case.game;

import entity.ClickableObject;
import entity.DialogueBox;

import java.util.List;

/**
 * Output data for the Game use case.
 */
public class GameOutputData {

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

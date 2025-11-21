package use_case.switch_to_game;

import entity.ClickableObject;
import entity.DialogueBox;

import java.util.List;

/**
 * Output data for the Switch to Game use case.
 */
public class SwitchToGameOutputData {

    private String backgroundImage;
    private List<ClickableObject> clickables;
    private DialogueBox currentDialogue;

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public List<ClickableObject> getClickableObjects() {
        return clickables;
    }

    public void setClickableObjects(List<ClickableObject> clickable) {
        this.clickables = clickable;
    }

    public DialogueBox getCurrentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogue(DialogueBox currentDialogue) {
        this.currentDialogue = currentDialogue;
    }
}

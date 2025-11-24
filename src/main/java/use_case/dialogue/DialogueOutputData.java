package use_case.dialogue;

import entity.ClickableObject;
import entity.Collectibles;
import entity.DialogueBox;

import java.util.List;

public class DialogueOutputData {

    private DialogueBox currentDialogue;

    public DialogueBox getCurrentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogue(DialogueBox currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

}

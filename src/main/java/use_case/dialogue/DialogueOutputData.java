package use_case.dialogue;

import entity.DialogueBox;

public class DialogueOutputData {

    private DialogueBox currentDialogue;

    public DialogueBox getCurrentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogue(DialogueBox currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

}

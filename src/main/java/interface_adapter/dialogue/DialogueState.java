package interface_adapter.dialogue;

import entity.DialogueBox;

public class DialogueState {

    private DialogueBox currentDialogue;

    public DialogueBox getCurrentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogue(DialogueBox currentDialogue) {
        this.currentDialogue = currentDialogue;
    }
}

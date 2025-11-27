package use_case.dialogue;

import entity.ClickableObject;
import entity.DialogueOption;
import entity.NonPlayableCharacter;
import use_case.game.GameInputData;

public class DialogueInteractor implements DialogueInputBoundary {
    private final DialogueOutputBoundary presenter;
    private final DialogueDataAccessInterface dialogueDataAccessInterface;


    public DialogueInteractor(DialogueDataAccessInterface gameDataAccessInterface, DialogueOutputBoundary dialogueOutputBoundary) {
        this.presenter = dialogueOutputBoundary;
        this.dialogueDataAccessInterface = gameDataAccessInterface;
    }

    public void executeDialogueOption(DialogueOption dialogueOption) {

        if (dialogueOption.leadsToScene()) {
            // Close dialogue and navigate to scene
            dialogueDataAccessInterface.setCurrentDialogue(null);
            dialogueDataAccessInterface.setCurrentScene(dialogueOption.getTargetScene());
        } else if (dialogueOption.leadsToDialogue()) {
            // Show next dialogue
            dialogueDataAccessInterface.setCurrentDialogue(dialogueOption.getTargetDialogue());
        }

        // Update game UI
        updateView();
    }

    @Override
    public void execute(GameInputData gameInputData) {
        ClickableObject clicked = gameInputData.getClickableObject();

        if (clicked instanceof NonPlayableCharacter) {
            dialogueDataAccessInterface.setCurrentDialogue(((NonPlayableCharacter) clicked).getDB());
        }
        // Update game UI
        updateView();
    }

    private void updateView() {
        DialogueOutputData dialogueOutputData = new DialogueOutputData();
        dialogueOutputData.setCurrentDialogue(dialogueDataAccessInterface.getCurrentDialogue());
        presenter.prepareView(dialogueOutputData);
    }
}

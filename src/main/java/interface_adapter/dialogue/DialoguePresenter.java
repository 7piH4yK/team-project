package interface_adapter.dialogue;

import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import use_case.dialogue.DialogueOutputBoundary;
import use_case.dialogue.DialogueOutputData;

public class DialoguePresenter implements DialogueOutputBoundary {

    private final GameViewModel dialogueViewModel;

    public DialoguePresenter(GameViewModel dialogueViewModel) {
        this.dialogueViewModel = dialogueViewModel;
    }

    @Override
    public void prepareView(DialogueOutputData outputData) {
        GameState state = dialogueViewModel.getState();

        state.setCurrentDialogue(outputData.getCurrentDialogue());

        dialogueViewModel.setState(state);
        dialogueViewModel.firePropertyChange();
    }
}

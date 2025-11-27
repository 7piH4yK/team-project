package use_case.switch_to_game;

import entity.Scene;

/**
 * The Switch to Game View Interactor.
 */
public class SwitchToGameViewInteractor implements SwitchToGameViewInputBoundary {
    private final SwitchToGameViewDataAccessInterface gameDataAccessObject;
    private final SwitchToGameViewOutputBoundary presenter;

    public SwitchToGameViewInteractor(SwitchToGameViewDataAccessInterface gameDataAccessInterface,
                                      SwitchToGameViewOutputBoundary switchToGameViewOutputBoundary) {
        this.gameDataAccessObject = gameDataAccessInterface;
        this.presenter = switchToGameViewOutputBoundary;
    }

    @Override
    public void execute() {
        gameDataAccessObject.resetGame();
        Scene currentScene = gameDataAccessObject.getCurrentScene();
        SwitchToGameOutputData gameOutputData = new SwitchToGameOutputData();
        gameOutputData.setBackgroundImage(currentScene.getImage());
        gameOutputData.setClickableObjects(currentScene.getObjects());
        gameOutputData.setCurrentDialogue(null); // No dialogue when first entering game
        presenter.switchToGameView(gameOutputData);
    }
}

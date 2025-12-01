package use_case.save;

import java.util.Map;

import entity.Player;
import entity.Scene;

/**
 * The save interactor.
 */
public class SaveInteractor implements SaveInputBoundary {
    private final SaveDataAccessInterface saveDataAccessObject;
    private final SaveOutputBoundary presenter;

    public SaveInteractor(SaveDataAccessInterface saveDataAccessInterface,
                          SaveOutputBoundary saveOutputBoundary) {
        this.saveDataAccessObject = saveDataAccessInterface;
        this.presenter = saveOutputBoundary;
    }

    @Override
    public void execute() {
        final Scene currentScene = saveDataAccessObject.getCurrentScene();
        final Map<String, Scene> scenes = saveDataAccessObject.getScenes();
        final Player player = saveDataAccessObject.getPlayer();

        final SaveOutputData saveOutputData = new SaveOutputData();
        saveOutputData.setPlayer(player);
        saveOutputData.setScenes(scenes);
        saveOutputData.setCurrentScene(currentScene);
        saveDataAccessObject.saveGame(saveOutputData);
        presenter.switchToSaveView();
    }
}

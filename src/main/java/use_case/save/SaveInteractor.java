package use_case.save;

import entity.Player;
import entity.Scene;

import java.util.Map;


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
        Scene currentScene = saveDataAccessObject.getCurrentScene();
        Map<String, Scene> scenes = saveDataAccessObject.getScenes();
        Player player = saveDataAccessObject.getPlayer();

        SaveOutputData saveOutputData = new SaveOutputData();
        saveOutputData.setPlayer(player);
        saveOutputData.setScenes(scenes);
        saveOutputData.setCurrentScene(currentScene);
        saveDataAccessObject.saveGame(saveOutputData);
        presenter.switchToSaveView();
    }
}

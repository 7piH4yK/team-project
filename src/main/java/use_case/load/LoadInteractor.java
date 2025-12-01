package use_case.load;

import java.io.File;

import entity.Scene;

/**
 * The save interactor.
 */
public class LoadInteractor implements LoadInputBoundary {
    private final LoadDataAccessInterface loadDataAccessObject;
    private final LoadOutputBoundary presenter;

    public LoadInteractor(LoadDataAccessInterface loadDataAccessObject, LoadOutputBoundary presenter) {
        this.loadDataAccessObject = loadDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        final String filename = "src/main/java/data_access/game_saves/save.json";
        final File saveFile = new File(filename);

        if (!saveFile.exists()) {
            presenter.displayError("No saved game found!");
        }
        else {
            loadDataAccessObject.loadGame(filename);

            final Scene currentScene = loadDataAccessObject.getCurrentScene();
            final LoadOutputData loadOutputData = new LoadOutputData();
            loadOutputData.setBackgroundImage(currentScene.getImage());
            loadOutputData.setClickableObjects(currentScene.getObjects());
            presenter.switchToLoadView(loadOutputData);
        }
    }
}

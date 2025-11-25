package use_case.collect_item;

import use_case.game.GameDataAccessInterface;
import entity.ClickableObject;
import entity.Collectibles;
import entity.Scene;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CollectItemInteractor implements CollectItemInputBoundary {

    private final GameDataAccessInterface dao;
    private final CollectItemOutputBoundary presenter;

    public CollectItemInteractor(GameDataAccessInterface dao,
                                 CollectItemOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void collect(CollectItemInputData inputData) {

        Scene scene = dao.getScenes().get(inputData.getSceneName());
        if (scene == null) {
            presenter.prepareFailView("Scene not found: " + inputData.getSceneName());
            return;
        }

        ClickableObject target = scene.getObjects().stream()
                .filter(o -> o.getName().equals(inputData.getObjectName()))
                .findFirst()
                .orElse(null);

        if (!(target instanceof Collectibles)) {
            presenter.prepareFailView("This object cannot be collected.");
            return;
        }

        dao.getPlayer().addToInventory((Collectibles) target);

        List<ClickableObject> updated = new ArrayList<>(scene.getObjects());
        updated.removeIf(o -> o.getName().equals(target.getName()));

        Scene updatedScene = new Scene(scene.getName(), updated, scene.getImage());
        dao.getScenes().put(updatedScene.getName(), updatedScene);
        dao.setCurrentScene(updatedScene);

        presenter.prepareSuccessView(
                new CollectItemOutputData(updatedScene, target.getName())
        );
    }
}

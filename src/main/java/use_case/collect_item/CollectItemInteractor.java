package use_case.collect_item;

import java.util.ArrayList;
import java.util.List;

import entity.ClickableObject;
import entity.Collectibles;
import entity.Scene;
import use_case.game.GameDataAccessInterface;

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

        final Scene scene = dao.getScenes().get(inputData.getSceneName());
         if (scene == null) {
            presenter.prepareFailView("Scene not found: " + inputData.getSceneName());
            return;
         }

        final ClickableObject target = scene.getObjects().stream()
                .filter(clickableO -> clickableO.getName().equals(inputData.getObjectName()))
                .findFirst()
                .orElse(null);

        if (!(target instanceof Collectibles)) {
            presenter.prepareFailView("This object cannot be collected.");
        }

        dao.getPlayer().addToInventory((Collectibles) target);

        final List<ClickableObject> updated = new ArrayList<>(scene.getObjects());
        updated.removeIf(clickableO -> clickableO.getName().equals(target.getName()));

        final Scene updatedScene = new Scene(scene.getName(), updated, scene.getImage());
        dao.getScenes().put(updatedScene.getName(), updatedScene);
        dao.setCurrentScene(updatedScene);

        presenter.prepareSuccessView(
                new CollectItemOutputData(updatedScene, target.getName(), dao.getPlayer().getInventory())
        );
    }
}

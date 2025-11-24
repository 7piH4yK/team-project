package use_case.collect_item;

import entity.ClickableObject;
import entity.Collectibles;
import entity.Scene;
import entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CollectItemInteractor implements CollectItemInputBoundary {

    private final CollectItemDataAccessInterface dao;
    private final CollectItemOutputBoundary presenter;

    public CollectItemInteractor(CollectItemDataAccessInterface dao,
                                 CollectItemOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void collect(CollectItemInputData input) {

        Scene scene = dao.getScene(input.getSceneName());
        Player player = dao.getPlayer();

        // find object
        ClickableObject target = scene.getObjects().stream()
                .filter(o -> o.getName().equals(input.getObjectName()))
                .findFirst()
                .orElse(null);

        if (!(target instanceof Collectibles)) {
            presenter.prepareFailView("This object cannot be collected.");
            return;
        }

        // add to inventory
        player.addToInventory((Collectibles) target);
        // remove from scene
        List<ClickableObject> updatedObjects = new ArrayList<>(scene.getObjects());
        updatedObjects.remove(target);

        Scene updatedScene =
                new Scene(scene.getName(), updatedObjects, scene.getImage());

        presenter.prepareSuccessView(
                new CollectItemOutputData(updatedScene, target.getName())
        );

        dao.savePlayer(player);
        dao.saveScene(updatedScene);

    }
}

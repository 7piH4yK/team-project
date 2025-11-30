package use_case.collect_item;

import java.util.List;

import entity.Collectibles;
import entity.Scene;

public class CollectItemOutputData {

    private final Scene updatedScene;
    private final String collectedItemName;
    private final List<Collectibles> updatedInventory;

    public CollectItemOutputData(Scene updatedScene,
                                 String collectedItemName,
                                 List<Collectibles> updatedInventory) {
        this.updatedScene = updatedScene;
        this.collectedItemName = collectedItemName;
        this.updatedInventory = updatedInventory;
    }

    public Scene getUpdatedScene() {
        return updatedScene;
    }

    public String getCollectedItemName() {
        return collectedItemName;
    }

    public List<Collectibles> getUpdatedInventory() {
        return updatedInventory;
    }
}

package use_case.collect_item;

import entity.Scene;

public class CollectItemOutputData {
    private final Scene updatedScene;
    private final String collectedItemName;

    public CollectItemOutputData(Scene updatedScene, String collectedItemName) {
        this.updatedScene = updatedScene;
        this.collectedItemName = collectedItemName;
    }

    public Scene getUpdatedScene() {
        return updatedScene;
    }

    public String getCollectedItemName() {
        return collectedItemName;
    }
}

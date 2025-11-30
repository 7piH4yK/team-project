package use_case.collect_item;

public class CollectItemInputData {
    private final String objectName;
    private final String sceneName;

    public CollectItemInputData(String objectName, String sceneName) {
        this.objectName = objectName;
        this.sceneName = sceneName;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getSceneName() {
        return sceneName;
    }
}

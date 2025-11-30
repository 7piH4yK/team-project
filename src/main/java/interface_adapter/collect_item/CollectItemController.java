package interface_adapter.collect_item;

import use_case.collect_item.CollectItemInputBoundary;
import use_case.collect_item.CollectItemInputData;

public class CollectItemController {

    private final CollectItemInputBoundary interactor;

    public CollectItemController(CollectItemInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void collectItem(String objectName, String sceneName) {
        CollectItemInputData inputData = new CollectItemInputData(objectName, sceneName);
        interactor.collect(inputData);
    }
}

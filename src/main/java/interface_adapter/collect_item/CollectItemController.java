package interface_adapter.collect_item;

import use_case.collect_item.CollectItemInputBoundary;
import use_case.collect_item.CollectItemInputData;

public class CollectItemController {

    private final CollectItemInputBoundary collectItemInteractor;

    public CollectItemController(CollectItemInputBoundary collectItemInteractor) {
        this.collectItemInteractor = collectItemInteractor;
    }

    public void collectItem(String objectName, String sceneName) {
        CollectItemInputData inputData = new CollectItemInputData(objectName, sceneName);
        collectItemInteractor.collect(inputData);
    }
}


package interface_adapter.collect_item;

import use_case.collect_item.CollectItemInputBoundary;
import use_case.collect_item.CollectItemInputData;

public class CollectItemController {

    private final CollectItemInputBoundary interactor;

    /**
     * Creates a new controller for the Collect Item use case.
     *
     * @param interactor the use case interactor that handles business logic
     */
    public CollectItemController(CollectItemInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Initiates the Collect Item use case.
     *
     * <p>
     * This method is typically called when the player clicks on an
     * object in the game UI. It constructs a {@link CollectItemInputData}
     * object containing:
     * <ul>
     *     <li>The name of the object to be collected</li>
     *     <li>The name of the scene in which the object currently exists</li>
     * </ul>
     * and forwards it to the interactor.
     * </p>
     *
     * @param objectName the name of the object the player clicked on
     * @param sceneName the name of the current scene
     */
    public void collectItem(String objectName, String sceneName) {
        final CollectItemInputData inputData = new CollectItemInputData(objectName, sceneName);
        interactor.collect(inputData);
    }
}

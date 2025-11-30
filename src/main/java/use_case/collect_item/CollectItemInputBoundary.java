package use_case.collect_item;

public interface CollectItemInputBoundary {
    /**
     * Executes the collect-item use case.
     *
     *  <p>
     * The interactor will:
     * <ol>
     *     <li>Locate the specified scene and clickable object</li>
     *     <li>Validate that the object is collectible</li>
     *     <li>Update the game state by removing the item from the scene
     *         and adding it to the player's inventory</li>
     *     <li>Produce response data and forward it to the presenter</li>
     * </ol>
     * </p>
     *
     * @param inputData the request model containing the scene name and item name
     */
    void collect(CollectItemInputData inputData);
}

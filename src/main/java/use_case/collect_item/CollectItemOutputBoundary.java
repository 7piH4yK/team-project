package use_case.collect_item;

public interface CollectItemOutputBoundary {
    /**
     * Sends a successful response to the presenter.
     *
     * <p>
     * This method is called when the specified item is successfully
     * collected. The provided output data includes:
     * <ul>
     *     <li>The updated scene with the item removed</li>
     *     <li>The name of the collected item</li>
     *     <li>The player's updated inventory</li>
     * </ul>
     * The presenter is responsible for converting this data into a view model,
     * updating UI state, or triggering view changes.
     * </p>
     *
     * @param outputData the result of the successful collect-item operation
     */
    void prepareSuccessView(CollectItemOutputData outputData);

    /**
     * Sends a failure response to the presenter.
     *
     * <p>
     * This method is called when the collect-item operation cannot be
     * completed. Common causes include:
     * <ul>
     *     <li>The scene specified in the request does not exist</li>
     *     <li>The item is not found in the scene</li>
     *     <li>The target object is not collectible</li>
     * </ul>
     * Implementations may choose how to present the error message:
     * update the UI, show a popup, log it, etc.
     * </p>
     *
     * @param errorMessage a human-readable description of the failure
     */
    void prepareFailView(String errorMessage);
}

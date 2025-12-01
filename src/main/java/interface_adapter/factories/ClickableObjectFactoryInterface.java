package interface_adapter.factories;

import entity.ClickableObject;
import entity.Collectibles;

/**
 * Factory interface for creating ClickableObjects and Collectibles.
 */
public interface ClickableObjectFactoryInterface {

    /**
     * Creates a new Collectibles instance.
     *
     * @param name   the name or identifier of the collectible
     * @param coordX the X-coordinate of the object within the scene
     * @param coordY the Y-coordinate of the object within the scene
     * @param image  the path to the object's image asset
     * @return a new Collectibles object with the specified parameters
     */
    Collectibles createCollectibles(String name, int coordX, int coordY, String image);

    /**
     * Creates a new ClickableObject instance.
     *
     * @param name   the name or identifier of the object
     * @param coordX the X-coordinate of the object within the scene
     * @param coordY the Y-coordinate of the object within the scene
     * @param image  the path to the object's image asset
     * @return a new ClickableObject with the specified parameters
     */
    ClickableObject create(String name, int coordX, int coordY, String image);
}

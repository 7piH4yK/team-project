package interface_adapter.factories;

import java.util.List;

import entity.ClickableObject;
import entity.Scene;

/**
 * Factory interface for creating Scene objects.
 */
public interface SceneFactoryInterface {
    /**
     * Creates instance of SceneFactory.
     *
     * @param name is the name of the Scene.
     * @param objects is the list of ClickableObjects.
     * @param image is the name of the image.
     * @return a Scene object.
     */
    Scene create(String name, List<ClickableObject> objects, String image);
}

package entity;

import interface_adapter.factories.SceneFactoryInterface;

import java.util.List;

/**
 * Factory for creating Scene objects.
 */
public class SceneFactory implements SceneFactoryInterface {

    public Scene create(String name, List<ClickableObject> objects, String image) {
        return new Scene(name, objects, image);
    }
}

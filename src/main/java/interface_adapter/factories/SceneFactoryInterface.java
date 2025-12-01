package interface_adapter.factories;

import java.util.List;

import entity.ClickableObject;
import entity.Scene;

public interface SceneFactoryInterface {
    Scene create(String name, List<ClickableObject> objects, String image);
}

package entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a scene in the game containing clickable objects.
 */
public class Scene {

    private final String name;
    private final List<ClickableObject> objects;
    private final String image;

    /**
     * Creates a new scene.
     * @param name the name of the scene
     * @param objects the list of clickable objects in this scene
     * @param image the path to the background image
     * @throws IllegalArgumentException if the name or image path is empty
     */
    public Scene(String name, List<ClickableObject> objects, String image) {
        if ("".equals(name)) {
            throw new IllegalArgumentException("Scene name cannot be empty");
        }
        if ("".equals(image)) {
            throw new IllegalArgumentException("Image path cannot be empty");
        }
        this.name = name;
        this.objects = new ArrayList<>(objects);
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public List<ClickableObject> getObjects() {
        return new ArrayList<>(objects);
    }

    public String getImage() {
        return image;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("image", this.image);

        JSONArray objectsArr = new JSONArray();
        for (ClickableObject obj : this.objects) {
            objectsArr.put(obj.toJson());
        }
        json.put("objects", objectsArr);

        return json;
    }

    public static Scene fromJson(JSONObject json) {
        String name = json.getString("name");
        String image = json.getString("image");
        JSONArray objArray = json.getJSONArray("objects");

        List<ClickableObject> objects = new ArrayList<>();
        for (int i = 0; i < objArray.length(); i++) {
            objects.add(ClickableObject.fromJson(objArray.getJSONObject(i)));
        }

        return new Scene(name, objects, image);
    }
    public void addObject(ClickableObject object) {objects.add(object);}
}

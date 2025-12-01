package entity;

import org.json.JSONObject;

public class ClickableObject {

    private final String name;
    private final int coordinateX;
    private final int coordinateY;
    private final String image;

    /**
     * Creates a new clickable object.
     *
     * @param name       the object's unique name or identifier
     * @param coordX     the X-coordinate of the object within the scene
     * @param coordY     the Y-coordinate of the object within the scene
     * @param image      the path to the object's image asset
     */
    public ClickableObject(String name, int coordX, int coordY, String image) {
        this.name = name;
        this.coordinateX = coordX;
        this.coordinateY = coordY;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public String getImage() {
        return image;
    }

    /**
     * Creates a {@code ClickableObject} instance from a JSON representation.
     *
     * <p>The JSON object must contain:</p>
     * <ul>
     *     <li>{@code "name"} – object name</li>
     *     <li>{@code "coordinateX"} – X-coordinate</li>
     *     <li>{@code "coordinateY"} – Y-coordinate</li>
     *     <li>{@code "image"} – image file path</li>
     * </ul>
     *
     * @param json the JSON object containing object data
     * @return a new {@code ClickableObject} created from the JSON fields
     */
    public static ClickableObject fromJson(JSONObject json) {
        return new ClickableObject(
                json.getString("name"),
                json.getInt("coordinateX"),
                json.getInt("coordinateY"),
                json.getString("image")
        );
    }

    /**
     * Converts this object into a JSON representation.
     *
     * @return a {@code JSONObject} containing all object fields
     */
    public JSONObject toJson() {
        final JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("coordinateX", this.coordinateX);
        json.put("coordinateY", this.coordinateY);
        json.put("image", this.image);
        return json;
    }

}

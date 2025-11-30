package entity;

import org.json.JSONObject;

/**
 * Represents a clickable object in the game that can be interacted with.
 */
public class Collectibles extends ClickableObject {

    private final String name;
    private final int coordinateX;
    private final int coordinateY;
    private final String image;

    /**
     * Creates a new clickable object.
     *
     * @param name        the name of the object
     * @param coordinateX the x coordinate of the object
     * @param coordinateY the y coordinate of the object
     * @param image       the path to the image asset
     * @throws IllegalArgumentException if the name or image path is empty
     */
    public Collectibles(String name, int coordinateX, int coordinateY, String image) {
        super(name, coordinateX, coordinateY, image);
        if ("".equals(name)) {
            throw new IllegalArgumentException("Object name cannot be empty");
        }
        if ("".equals(image)) {
            throw new IllegalArgumentException("Image path cannot be empty");
        }
        this.name = name;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.image = image;
    }

    /**
     * Creates a {@code Collectibles} instance from a JSON representation.
     *
     * <p>The JSON must contain the fields:</p>
     * <ul>
     *     <li>{@code "name"}</li>
     *     <li>{@code "coordinateX"}</li>
     *     <li>{@code "coordinateY"}</li>
     *     <li>{@code "image"}</li>
     * </ul>
     *
     * @param json the JSON object containing collectible data
     * @return a new {@code Collectibles} instance
     */
    public static Collectibles fromJson(JSONObject json) {
        return new Collectibles(
                json.getString("name"),
                json.getInt("coordinateX"),
                json.getInt("coordinateY"),
                json.getString("image"));
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
     * Converts this collectible into a JSON representation.
     *
     * @return a {@link JSONObject} containing the collectible's attributes
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

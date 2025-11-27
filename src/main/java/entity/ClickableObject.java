package entity;

import org.json.JSONObject;


public class ClickableObject {

    private final String name;
    private final int coordinateX;
    private final int coordinateY;
    private final String image;

    public ClickableObject(String name, int x, int y, String image) {
        this.name = name;
        this.coordinateX = x;
        this.coordinateY = y;
        this.image = image;
    }

    public static ClickableObject fromJson(JSONObject json) {
        return new ClickableObject(
                json.getString("name"),
                json.getInt("coordinateX"),
                json.getInt("coordinateY"),
                json.getString("image")
        );
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

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("coordinateX", this.coordinateX);
        json.put("coordinateY", this.coordinateY);
        json.put("image", this.image);
        return json;
    }


}

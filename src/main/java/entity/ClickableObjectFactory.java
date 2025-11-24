package entity;

public class ClickableObjectFactory {

    // New: explicit collectable flag
    public ClickableObject create(String name, int coordinateX, int coordinateY, String image) {
        return new ClickableObject(name, coordinateX, coordinateY, image);
    }

    // Keep old calls working (defaults to false)
    public Collectibles createCollectibles(String name, int coordinateX, int coordinateY, String image) {
        return new Collectibles(name, coordinateX, coordinateY, image);
    }
}

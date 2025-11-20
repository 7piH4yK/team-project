package entity;

public class ClickableObjectFactory {

    // New: explicit collectable flag
    public ClickableObject create(String name, int x, int y, String image) {
        return new ClickableObject(name, x, y, image);
    }

    // Keep old calls working (defaults to false)
    public Collectibles createCollectibles(String name, int x, int y, String image, Boolean collectable) {
        return new Collectibles(name, x, y, image, collectable);
    }
}

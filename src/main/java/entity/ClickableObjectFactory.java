package entity;

public class ClickableObjectFactory {

    // New: explicit collectable flag
    public ClickableObject create(String name, int x, int y, String image, boolean collectable) {
        return new ClickableObject(name, x, y, image, collectable);
    }

    // Keep old calls working (defaults to false)
    public ClickableObject create(String name, int x, int y, String image) {
        return new ClickableObject(name, x, y, image, false);
    }
}

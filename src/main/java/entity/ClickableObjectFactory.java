package entity;

public class ClickableObjectFactory {

    /**
     * Creates a new {@link Collectibles} instance.
     *
     * @param name     the name or identifier of the collectible
     * @param coordX   the X-coordinate of the object within the scene
     * @param coordY   the Y-coordinate of the object within the scene
     * @param image    the path to the object's image asset
     * @return a new {@code Collectibles} object with the specified parameters
     */
    public ClickableObject create(String name, int coordX, int coordY, String image) {
        return new ClickableObject(name, coordX, coordY, image);
    }
    /**
     * Creates a new {@link Collectibles} instance.
     *
     * @param name     the name or identifier of the collectible
     * @param coordX   the X-coordinate of the object within the scene
     * @param coordY   the Y-coordinate of the object within the scene
     * @param image    the path to the object's image asset
     * @return a new {@code Collectibles} object with the specified parameters
     */
    public Collectibles createCollectibles(String name, int coordX, int coordY, String image) {
        return new Collectibles(name, coordX, coordY, image);
    }
}

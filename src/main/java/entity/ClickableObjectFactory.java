package entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Factory that can create different kinds of ClickableObject.
 * Keep this class in the entities (or a factory) package depending on your layering rules.
 */
public class ClickableObjectFactory {

    /**
     * Create a scene-changing clickable.
     *
     * @param imagePath image resource path (e.g., "/images/door.png")
     * @param width     desired width (pass <=0 to keep image's intrinsic size)
     * @param height    desired height (pass <=0 to keep image's intrinsic size)
     * @param onSceneChange action to run for scene change
     */
    public static ClickableObject createSceneChangeObject(
            String imagePath,
            double width,
            double height,
            Runnable onSceneChange
    ) {
        ImageView iv = buildImageView(imagePath, width, height);
        return new SceneChangeObject(iv, onSceneChange);
    }

    /**
     * Create a collectible clickable.
     *
     * @param imagePath image resource path
     * @param width     desired width
     * @param height    desired height
     * @param onCollect action to run when collected (e.g., inventory.add("key"))
     * @param autoHide  whether to hide after collecting
     */
    public static ClickableObject createCollectableObject(
            String imagePath,
            double width,
            double height,
            Runnable onCollect,
            boolean autoHide
    ) {
        ImageView iv = buildImageView(imagePath, width, height);
        return new CollectableObject(iv, onCollect, autoHide);
    }

    private static ImageView buildImageView(String imagePath, double w, double h) {
        Image image = new Image(imagePath);
        ImageView iv = new ImageView(image);
        if (w > 0) iv.setFitWidth(w);
        if (h > 0) iv.setFitHeight(h);
        iv.setPreserveRatio(true);
        iv.setPickOnBounds(true); // easier to click
        return iv;
    }
}

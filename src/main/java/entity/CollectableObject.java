package entity;

import javafx.scene.image.ImageView;

/**
 * A clickable that represents a collectible (e.g., coin, key).
 * On click, executes the collect action and then deactivates (hides) itself by default.
 */
public class CollectableObject implements ClickableObject {
    private final ImageView imageView;
    private final Runnable collectAction;
    private boolean active = true;
    private final boolean autoHide;

    /**
     * @param imageView     the node to show in the scene
     * @param collectAction code that updates inventory/score/etc.
     * @param autoHide      whether to hide after collecting
     */
    public CollectableObject(ImageView imageView, Runnable collectAction, boolean autoHide) {
        this.imageView = imageView;
        this.collectAction = collectAction;
        this.autoHide = autoHide;

        // Wire click
        this.imageView.setOnMouseClicked(e -> {
            if (active) onClick();
        });
    }

    public CollectableObject(ImageView imageView, Runnable collectAction) {
        this(imageView, collectAction, true);
    }

    @Override
    public void onClick() {
        if (collectAction != null) {
            collectAction.run();
        }
        if (autoHide) {
            deactivate();
        }
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void deactivate() {
        active = false;
        imageView.setVisible(false);
        imageView.setMouseTransparent(true);
    }
}
